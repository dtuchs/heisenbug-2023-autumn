<script lang="ts">
    import { Avatar, getModalStore} from '@skeletonlabs/skeleton';
    import NewPaintingForm from '$lib/components/forms/painting/NewPaintingForm.svelte';
    import { prepareModal } from '$lib/helpers/prepareModal';
    import {singleArtistStore} from "$lib/stores/singleArtist.store";
    import {apiClient} from "$lib/api/apiClient";
    import {checkDuplicates} from "$lib/helpers/dataUtils";
    import type {PaintingType} from "$lib/types/Painting";
    import ListWrapper from "$lib/components/ListWrapper.svelte";
    import PaintingList from "$lib/components/PaintingList.svelte";
    import EditArtistForm from "$lib/components/forms/artist/EditArtistForm.svelte";
    import { page } from '$app/stores';
    import {sessionStore} from "$lib/stores/sessionStore";
    import EditComponent from "$lib/components/EditComponent.svelte";
    import {onMount} from "svelte";
    import {goto} from "$app/navigation";

    const modalStore = getModalStore();

    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    let currentPage = 0;
    const authorId = $page.params.id;

    onMount(async () => {
        singleArtistStore.set({
            data: undefined,
            paintings: [],
            noMoreData: true,
            isLoading: true,
            ignoreIds: [],
        });
        const artistData = await apiClient.loadArtist(authorId);
        const paintingsData = await apiClient.loadPaintingsByAuthorId({authorId: $page.params.id});
        if(artistData.error || paintingsData.error) {
            errorTrigger(artistData.error ?? paintingsData.error);
            singleArtistStore.update(() => {
                return {
                    noMoreData: true,
                    data: artistData?.data,
                    paintings: paintingsData?.data?.content,
                    isLoading: false,
                    ignoreIds: [],
                }
            });
            await goto("/not-found");
        }
        if(artistData.data && paintingsData.data) {
            singleArtistStore.update(() => {
                return {
                    data: artistData.data,
                    paintings: paintingsData.data?.content,
                    noMoreData: paintingsData.data ? currentPage === paintingsData.data?.totalPages - 1: true,
                    isLoading: false,
                    ignoreIds: [],
                }
            });
        }
    });

    const loadMore = async () => {
        singleArtistStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
                noMoreData: true,
            }
        });
        const response = await apiClient.loadPaintingsByAuthorId({authorId: authorId, page: ++currentPage});
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
                    noMoreData: resData.data ? currentPage === resData.data?.totalPages - 1: true,
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
                        dataTestId="edit-artist"
                        formData={{
                           name: $singleArtistStore?.data?.name,
                           photo: $singleArtistStore?.data?.photo,
                           biography: $singleArtistStore?.data?.biography,
                           id: authorId,
                        }}
                />
                <button class="btn variant-filled-primary m-3 mx-auto block w-full" type="button" on:click={clickAddButton}>Добавить картину</button>
            {/if}
        </div>
        <p class="col-span-2 w-4/5 m-2">{$singleArtistStore?.data?.biography}</p>
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