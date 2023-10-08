<script lang="ts">
    import {singlePaintingStore} from "$lib/stores/singlePainting.store.js";
    import EditPaintingForm from "$lib/components/forms/painting/EditPaintingForm.svelte";
    import type {PageData} from "../../../../.svelte-kit/types/src/routes/painting/[id]/$types.js";
    import EditComponent from "$lib/components/EditComponent.svelte";
    export let data: PageData;

    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    if(data.error) {
        errorTrigger(data.error);
    } else {
        singlePaintingStore.set({
            data: data.data,
        });
    }
</script>

<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-1 lg:grid-cols-2 m-4 min-h-full">
        <img src={$singlePaintingStore?.data?.content} alt={$singlePaintingStore?.data?.title} class="my-4 mx-auto w-100"/>
        <div>
            <div>
                <header class="card-header text-center font-bold">{$singlePaintingStore?.data?.title}</header>
                <div class="text-center">{$singlePaintingStore?.data?.artist?.name}</div>
                <div class="w-56 m-3 mx-auto">
                    <EditComponent
                            {errorTrigger}
                            {successTrigger}
                            formComponent={EditPaintingForm}
                            store={singlePaintingStore}
                            successMessage="Обновлена картина"
                            formData={{
                              painting: $singlePaintingStore.data,
                            }}
                            dataKey="title"
                    />
                </div>
            </div>
            <div class="m-4">{$singlePaintingStore?.data?.description}</div>
        </div>
    </div>
</article>