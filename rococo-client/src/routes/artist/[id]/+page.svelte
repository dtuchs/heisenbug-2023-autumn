<script lang="ts">
	import { Avatar, getModalStore} from '@skeletonlabs/skeleton';
	import type { PageData } from './$types';
    import NewPaintingForm from '$lib/components/forms/NewPaintingForm.svelte';
    import { prepareModal } from '$lib/helpers/prepareModal';
    import {singleArtistStore} from "$lib/stores/singleArtist.store";
    import {apiClient} from "$lib/helpers/apiClient";
    import {checkDuplicates} from "$lib/helpers/dataUtils";
    import type {PaintingType} from "$lib/types/Painting";
    import ListWrapper from "$lib/components/ListWrapper.svelte";
    import PaintingList from "$lib/components/PaintingList.svelte";

    const modalStore = getModalStore();

    export let data: PageData;

    let currentPage = 0;

    singleArtistStore.set({
        paintings: data.paintings,
        noMoreData: currentPage === data.totalPages - 1,
        isLoading: false,
        ignoreIds: [],
    });

    const loadMore = async () => {
        singleArtistStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
                noMoreData: true,
            }
        });
        const response = await apiClient.loadPaintingsByAuthorId({authorId: data.artist.id, page: ++currentPage});
        const newBatch = checkDuplicates<PaintingType>(response.content, singleArtistStore, $singleArtistStore.ignoreIds);
        singleArtistStore.update((prevState) => {
            return {
                ...prevState,
                paintings: [
                    ...prevState.paintings,
                    ...newBatch
                ],
                noMoreData: currentPage === response.totalPages -1,
                isLoading: false,
            }
        });
    }

    const paintingAddCallback = async (result: PaintingType) => {
        singleArtistStore.update((prevState) => {
            return {
                ...prevState,
                paintings: [
                    result,
                    ...prevState.paintings,
                ],
                ignoreIds: [...prevState.ignoreIds, result.id],
            }
        });
    }

    const clickAddButton = () => {
        const modal = prepareModal(
            NewPaintingForm,
            "Новая картина",
            "Заполните форму, чтобы добавить новую картину",
            paintingAddCallback);
        modalStore.trigger(modal);
    };
    
</script>


<article class="card m-6">
    <header class="card-header text-center font-bold text-2xl">{data?.artist?.name}</header>
    <section class="p-4 grid grid-cols-3 justify-items-center">
        <div class="flex flex-col">
            <Avatar src={data?.artist?.photo} width="w-56" rounded="rounded-full" class="col-span-1"/>
            <button class="btn variant-ghost m-3" type="button">Редактировать</button>
            <button class="btn variant-filled-primary m-3" type="button" on:click={clickAddButton}>Добавить картину</button>
        </div>
        <p class="col-span-2">{data?.artist?.biography}</p>
    </section>
    <section class="p-4">
        <ListWrapper data={$singleArtistStore.paintings}
                     isSearchNotEmpty={false}
                     emptySearchText="Картины не найдены"
                     emptySearchDescription="Для указанного вами фильтра мы не смогли не найти картины"
                     emptyStateTitle="Пока что список картин этого художника пуст."
                     emptyStateButtonName="Добавить картину"
                     noMoreData={$singleArtistStore.noMoreData}
                     isLoading={$singleArtistStore.isLoading}
                     {loadMore}
                     {clickAddButton}
                     bordered={false}
                     fullPage={false}
        >
            <PaintingList paintings={$singleArtistStore.paintings}/>
        </ListWrapper>
    </section>
</article>
	
