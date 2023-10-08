<script lang="ts">
    import { Avatar, getModalStore} from '@skeletonlabs/skeleton';
    import NewPaintingForm from '$lib/components/forms/painting/NewPaintingForm.svelte';
    import { prepareModal } from '$lib/helpers/prepareModal';
    import {singleArtistStore} from "$lib/stores/singleArtist.store";
    import {apiClient} from "$lib/helpers/apiClient";
    import {checkDuplicates} from "$lib/helpers/dataUtils";
    import type {PaintingType} from "$lib/types/Painting";
    import ListWrapper from "$lib/components/ListWrapper.svelte";
    import PaintingList from "$lib/components/PaintingList.svelte";
    import EditArtistForm from "$lib/components/forms/artist/EditArtistForm.svelte";
    import type {PageData} from "../../../../.svelte-kit/types/src/routes/artist/[id]/$types.js";
    import {sessionStore} from "$lib/stores/sessionStore";
    import EditComponent from "$lib/components/EditComponent.svelte";

    const modalStore = getModalStore();

    export let data: PageData;

    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;


    let currentPage = 0;

    if(data.artist.error || data.paintings.error) {
        errorTrigger(data.artist.error ?? data.paintings.error);
    }
    if(data.artist.data && data.paintings.data) {
        singleArtistStore.set({
            data: data.artist.data,
            paintings: data.paintings.data.content,
            noMoreData: currentPage === data.paintings.data.totalPages - 1,
            isLoading: false,
            ignoreIds: [],
        });
    }

    const loadMore = async () => {
        singleArtistStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
                noMoreData: true,
            }
        });
        const response = await apiClient.loadPaintingsByAuthorId({authorId: data.artist.data.id, page: ++currentPage});
        if (response.error) {
            singleArtistStore.update((prevState) => {
                return {
                    ...prevState,
                    isLoading: false,
                }
            });
            errorTrigger(response.error);
            return;
        }
        const resData = response.data;
        if(resData) {
            const newBatch = checkDuplicates<PaintingType>(resData.content, singleArtistStore, $singleArtistStore.ignoreIds);
            singleArtistStore.update((prevState) => {
                return {
                    ...prevState,
                    paintings: [
                        ...prevState.paintings,
                        ...newBatch
                    ],
                    noMoreData: currentPage === resData.totalPages -1,
                    isLoading: false,
                }
            });
        }
    }

    const paintingAddCallback = async (result: {
        data?: PaintingType,
        error?: string,
    })  => {
        if (result.error) {
            errorTrigger(result.error);
            return;
        }
        const data = result.data;
        if (data) {
            singleArtistStore.update((prevState) => {

                return {
                    ...prevState,
                    paintings: [
                        data,
                        ...prevState.paintings,
                    ],
                    ignoreIds: [...prevState.ignoreIds, data.id],
                }
            });
            successTrigger(`Добавлена картина: ${data.title}`);
        }
    }

    const clickAddButton = () => {
        const modal = prepareModal({
            ref: NewPaintingForm,
            valueAttr: {
                id: $singleArtistStore.data?.id,
            },
            callback: paintingAddCallback
        });
        modalStore.trigger(modal);
    };

</script>


<article class="card m-6">
    <header class="card-header text-center font-bold text-2xl">{$singleArtistStore?.data?.name}</header>
    <section class="p-4 grid grid-cols-1 lg:grid-cols-3 justify-items-center">
        <div class="flex flex-col">
            <Avatar src={$singleArtistStore?.data?.photo} width="w-56" rounded="rounded-full" class="col-span-1"/>
            {#if $sessionStore.user}
                <EditComponent
                        {errorTrigger}
                        {successTrigger}
                        formComponent={EditArtistForm}
                        store={singleArtistStore}
                        dataKey="name"
                        successMessage="Обновлен художник"
                        formData={{
                           name: $singleArtistStore?.data?.name,
                           photo: $singleArtistStore?.data?.photo,
                           biography: $singleArtistStore?.data?.biography,
                           id: data.artist.data.id,
                        }}
                />
                <button class="btn variant-filled-primary m-3 mx-auto block w-full" type="button" on:click={clickAddButton}>Добавить картину</button>
            {/if}
        </div>
        <p class="col-span-2 w-4/5">{$singleArtistStore?.data?.biography}</p>
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