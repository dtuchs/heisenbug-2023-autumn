<script lang="ts">
	import type { PageData } from './$types';
    import {prepareModal} from "$lib/helpers/prepareModal";
    import {singlePaintingStore} from "$lib/stores/singlePainting.store.js";
    import {getModalStore} from "@skeletonlabs/skeleton";
    import type {PaintingType} from "$lib/types/Painting";
    import EditPaintingForm from "$lib/components/forms/painting/EditPaintingForm.svelte";

    const modalStore = getModalStore();

    export let data: PageData;
    console.log(data);

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
        <img src={data?.painting?.content} alt={data?.painting?.title} class="max-w-xl my-4 mx-auto"/>
        <div>
            <div>
                <header class="card-header text-center font-bold">{data?.painting?.title}</header>
                <div class="text-center">{data?.artist?.name}</div>
                <button class="btn variant-ghost m-3 ml-auto block" type="button" on:click={clickEditButton}>Редактировать</button>
            </div>
            <div class="m-4">{data?.painting?.description}</div>
        </div>
    </div>
</article>