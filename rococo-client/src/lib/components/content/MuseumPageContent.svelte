<script lang="ts">
    import EditMuseumForm from "$lib/components/forms/museum/EditMuseumForm.svelte";
    import {singleMuseumStore} from "$lib/stores/singleMuseum.store";
    import type {PageData} from "../../../../.svelte-kit/types/src/routes/museum/[id]/$types.js";
    import EditComponent from "$lib/components/EditComponent.svelte";

    export let data: PageData;
    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    if(data.error) {
        errorTrigger(data.error);
    } else {
        singleMuseumStore.set({
            data: data.data,
        });
    }
</script>


<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-1 lg:grid-cols-2 m-4 min-h-full">
        <div>
            <header class="card-header text-center font-bold">{$singleMuseumStore.data?.title}</header>
            <div class="text-center">{$singleMuseumStore.data?.geo.country.name}, {$singleMuseumStore.data?.geo?.city}</div>
            <div class="w-56 m-3 mx-auto">
                <EditComponent
                        {errorTrigger}
                        {successTrigger}
                        formComponent={EditMuseumForm}
                        store={singleMuseumStore}
                        successMessage="Обновлен музей"
                        formData={{
                          museum: $singleMuseumStore.data,
                        }}
                        dataKey="title"
                />
            </div>
            <div>{$singleMuseumStore.data?.description}</div>
        </div>
        <img src={$singleMuseumStore.data?.photo} alt={$singleMuseumStore.data?.title} class="my-4 mx-auto"/>
    </div>
</article>