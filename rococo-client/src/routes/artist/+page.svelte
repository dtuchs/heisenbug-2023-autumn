<script lang="ts">
	import { getModalStore } from '@skeletonlabs/skeleton';
    import NewArtistForm from '$lib/components/forms/NewArtistForm.svelte';
    import type { PageData } from './$types';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import { apiClient } from '$lib/helpers/apiClient';
    import { artistsStore } from '$lib/stores/artist.store';
    import ArtistList from '$lib/components/ArtistList.svelte';
    import ListWrapper from "$lib/components/ListWrapper.svelte";

    export let data: PageData;
    let isSearchNotEmpty = false;
    const modalStore = getModalStore();

    let currentPage = 0;

    artistsStore.set({
        artists: data.artists,
        noMoreData: data.currentPage === data.totalPages - 1,
        isLoading: false,
    });

    const loadAuthors = async (search: string) => {
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
            }
        });
        const response = await apiClient.loadArtists({search});
        artistsStore.set({artists: response.content, noMoreData: true, isLoading: false});
        isSearchNotEmpty = search.length > 0;
	};

    const loadMore = async () => {
        artistsStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
            }
        });
        const response = await apiClient.loadArtists({page: ++currentPage});
        const newBatch = response.content;
        artistsStore.update((prevState) => {
            return {artists: [
                    ...prevState.artists,
                    ...newBatch
                ],
                noMoreData: currentPage === response.totalPages -1,
                isLoading: false,
            }
        });
    }
 
    const clickAddButton = () => {
        const modal = prepareModal(
            NewArtistForm, 
            "Новый художник", 
            "Заполните форму, чтобы добавить нового художника");
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

