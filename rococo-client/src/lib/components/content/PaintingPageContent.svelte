<script lang="ts">
    import {singlePaintingStore} from "$lib/stores/singlePainting.store.js";
    import EditPaintingForm from "$lib/components/forms/painting/EditPaintingForm.svelte";
    import EditComponent from "$lib/components/EditComponent.svelte";
    import { page } from '$app/stores';
    import {onMount} from "svelte";
    import {apiClient} from "$lib/api/apiClient";
    import {goto} from "$app/navigation";

    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    onMount(async () => {
        const data = await apiClient.loadPainting($page.params.id);
        if(data.error) {
            errorTrigger(data.error);
            await goto("/not-found");
        } else {
            singlePaintingStore.set({
                data: data.data,
            });
        }
    });

</script>

<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-1 lg:grid-cols-2 m-4 min-h-full">
        <img src={$singlePaintingStore?.data?.content} alt={$singlePaintingStore?.data?.title} class="my-4 mx-auto w-full"/>
        <div>
            <div>
                <header class="card-header text-center font-bold">{$singlePaintingStore?.data?.title}</header>
                <div class="text-center">{$singlePaintingStore?.data?.artist?.name}</div>
                <div class="w-56 m-3 mx-auto">
                    <EditComponent
                            dataTestId="edit-painting"
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