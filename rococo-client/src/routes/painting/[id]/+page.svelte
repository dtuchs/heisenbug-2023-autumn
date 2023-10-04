<script lang="ts">
	import type { PageData } from './$types';
    import {prepareModal} from "$lib/helpers/prepareModal";
    import {singlePaintingStore} from "$lib/stores/singlePainting.store.js";
    import {getModalStore} from "@skeletonlabs/skeleton";
    import type {PaintingType} from "$lib/types/Painting";
    import EditPaintingForm from "$lib/components/forms/painting/EditPaintingForm.svelte";
    import {sessionStore} from "$lib/stores/sessionStore";

    const modalStore = getModalStore();

    export let data: PageData;

    singlePaintingStore.set({
        painting: data.painting,
    });

    const paintingEditCallback = async (result: PaintingType) => {
        singlePaintingStore.update(() => {
            return {
                painting: result,
            }
        });
    }

    const clickEditButton = () => {
        const modal = prepareModal({
            ref: EditPaintingForm,
            title: "Редактировать картину",
            body: "",
            valueAttr: {
                painting: $singlePaintingStore.painting,
            },
            callback: paintingEditCallback,
        });
        modalStore.trigger(modal);
    };
</script>


<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-2 m-4 min-h-full">
        <img src={$singlePaintingStore?.painting?.content} alt={$singlePaintingStore?.painting?.title} class="max-w-xl my-4 mx-auto"/>
        <div>
            <div>
                <header class="card-header text-center font-bold">{$singlePaintingStore?.painting?.title}</header>
                <div class="text-center">{$singlePaintingStore?.painting?.artist?.name}</div>
                {#if $sessionStore.user}
                    <button class="btn variant-ghost m-3 ml-auto block" type="button" on:click={clickEditButton}>Редактировать</button>
                {/if}
            </div>
            <div class="m-4">{$singlePaintingStore?.painting?.description}</div>
        </div>
    </div>
</article>