<script lang="ts">
    import EditMuseumForm from "$lib/components/forms/museum/EditMuseumForm.svelte";
    import {singleMuseumStore} from "$lib/stores/singleMuseum.store";
    import { page } from '$app/stores';
    import EditComponent from "$lib/components/EditComponent.svelte";
    import {onMount} from "svelte";
    import {goto} from "$app/navigation";
    import {apiClient} from "$lib/api/apiClient";

    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    onMount(async () => {
        const data = await apiClient.loadMuseum($page.params.id);
        if(data.error) {
            errorTrigger(data.error);
            await goto("/not-found");
        } else {
            singleMuseumStore.set({
                data: data.data,
            });
        }
    });

</script>

<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-1 lg:grid-cols-2 m-4 min-h-full">
        <div>
            <header class="card-header text-center font-bold">{$singleMuseumStore.data?.title}</header>
            <div class="text-center">{$singleMuseumStore.data?.geo.country.name}, {$singleMuseumStore.data?.geo?.city}</div>
            <div class="w-56 m-3 mx-auto">
                <EditComponent
                        dataTestId="edit-museum"
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
        <img src={$singleMuseumStore.data?.photo} alt={$singleMuseumStore.data?.title} class="my-4 mx-auto w-full"/>
    </div>
</article>