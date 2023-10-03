<script lang="ts">
	import { getModalStore } from '@skeletonlabs/skeleton';
    import NewArtistForm from '$lib/components/forms/artist/NewArtistForm.svelte';
    import type { PageData } from './$types';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import { apiClient } from '$lib/helpers/apiClient';
    import { artistsStore } from '$lib/stores/artist.store';
    import ArtistList from '$lib/components/ArtistList.svelte';
    import ListWrapper from "$lib/components/ListWrapper.svelte";
    import type {ArtistType} from "$lib/types/Artist";
    import {checkDuplicates} from "$lib/helpers/dataUtils";

    export let data: PageData;
    let isSearchNotEmpty = false;
    const modalStore = getModalStore();

    let currentPage = 0;

    artistsStore.set({
        artists: data.artists,
        noMoreData: currentPage === data.totalPages - 1,
        isLoading: false,
        ignoreIds: [],
    });

    const loadAuthors = async (search: string) => {
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
            }
        });
        const response = await apiClient.loadArtists({search});
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                artists: response.content,
                isLoading: false,
                noMoreData: true,
            }
        });
        isSearchNotEmpty = search.length > 0;
	};

    const loadMore = async () => {
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
                noMoreData: true,
            }
        });
        const response = await apiClient.loadArtists({page: ++currentPage});
        const newBatch = checkDuplicates<ArtistType>(response.content, artistsStore, $artistsStore.ignoreIds);
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                artists: [
                    ...prevState.artists,
                    ...newBatch
                ],
                noMoreData: currentPage === response.totalPages -1,
                isLoading: false,
            }
        });
    }

    const artistAddCallback = async (result: ArtistType) => {
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                artists: [
                    result,
                    ...prevState.artists,
                ],
                ignoreIds: [...prevState.ignoreIds, result.id],
            }
        });
    }
 
    const clickAddButton = () => {
        const modal = prepareModal({
            ref: NewArtistForm,
            title: "Новый художник",
            body: "Заполните форму, чтобы добавить нового художника",
            callback: artistAddCallback});
        modalStore.trigger(modal);
    }
</script>

<ContentPageHeading
    title="Художники"
    searchPlaceholder="Искать художников..."
    addButtonName="Добавить художника"
    onAddButtonClick={clickAddButton}
    loadFunction={loadAuthors}
/>
<ListWrapper data={$artistsStore.artists}
             {isSearchNotEmpty}
             emptySearchText="Художники не найдены"
             emptySearchDescription="Для указанного вами фильтра мы не смогли не найти художников"
             emptyStateTitle="Пока что список художников пуст. Чтобы пополнить коллекцию, добавьте нового художника"
             emptyStateButtonName="Добавить художника"
             noMoreData={$artistsStore.noMoreData}
             isLoading={$artistsStore.isLoading}
             {loadMore}
             {clickAddButton}
>
    <ArtistList/>
</ListWrapper>

