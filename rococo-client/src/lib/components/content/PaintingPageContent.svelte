<script lang="ts">
    import {prepareModal} from "$lib/helpers/prepareModal";
    import {singlePaintingStore} from "$lib/stores/singlePainting.store.js";
    import {getModalStore} from "@skeletonlabs/skeleton";
    import type {PaintingType} from "$lib/types/Painting";
    import EditPaintingForm from "$lib/components/forms/painting/EditPaintingForm.svelte";
    import {sessionStore} from "$lib/stores/sessionStore";
    import type {PageData} from "../../../../.svelte-kit/types/src/routes/painting/[id]/$types.js";

    const modalStore = getModalStore();

    export let data: PageData;

    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    if(data.error) {
        errorTrigger(data.error);
    } else {
        singlePaintingStore.set({
            painting: data.data,
        });
    }

    const paintingEditCallback = async (result: {
        data?: PaintingType,
        error?: string,
    }) => {
        if(result.error) {
            errorTrigger(result.error);
            return;
        }
        const data = result.data;
        if(data) {
            singlePaintingStore.update(() => {
                return {
                    painting: data,
                }
            });
            successTrigger(`Обновлена картина: ${data.title}`);
        }
    }

    const clickEditButton = () => {
        const modal = prepareModal({
            ref: EditPaintingForm,
            title: "Редактировать картину",
            valueAttr: {
                painting: $singlePaintingStore.painting,
            },
            callback: paintingEditCallback,
        });
        modalStore.trigger(modal);
    };
</script>

<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-1 lg:grid-cols-2 m-4 min-h-full">
        <img src={$singlePaintingStore?.painting?.content} alt={$singlePaintingStore?.painting?.title} class="my-4 mx-auto w-100"/>
        <div>
            <div>
                <header class="card-header text-center font-bold">{$singlePaintingStore?.painting?.title}</header>
                <div class="text-center">{$singlePaintingStore?.painting?.artist?.name}</div>
                {#if $sessionStore.user}
                    <button class="btn variant-ghost m-3 mx-auto lg:mr-3 block" type="button" on:click={clickEditButton}>Редактировать</button>
                {/if}
            </div>
            <div class="m-4">{$singlePaintingStore?.painting?.description}</div>
        </div>
    </div>
</article>