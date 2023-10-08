<script lang="ts">
    import {prepareModal} from "$lib/helpers/prepareModal";
    import {getModalStore} from "@skeletonlabs/skeleton";
    import EditMuseumForm from "$lib/components/forms/museum/EditMuseumForm.svelte";
    import {singleMuseumStore} from "$lib/stores/singleMuseum.store";
    import type {MuseumType} from "$lib/types/Museum";
    import {sessionStore} from "$lib/stores/sessionStore";
    import type {PageData} from "../../../../.svelte-kit/types/src/routes/museum/[id]/$types.js";

    const modalStore = getModalStore();
    export let data: PageData;
    export let errorTrigger: (message: string) => void;
    export let successTrigger: (message: string) => void;

    if(data.error) {
        errorTrigger(data.error);
    } else {
        singleMuseumStore.set({
            museum: data.data,
        });
    }

    const museumEditCallback = async (result: {
        data?: MuseumType,
        error?: string,
    }) => {
        if(result.error) {
            errorTrigger(result.error);
            return;
        }
        const data = result.data;
        if(data) {
            singleMuseumStore.update(() => {
                return {
                    museum: data,
                }
            });
            successTrigger(`Обновлен музей: ${data.title}`);
        }
    }

    const clickEditButton = () => {
        const modal = prepareModal({
            ref: EditMuseumForm,
            title: "Редактировать музей",
            valueAttr: {
                museum: $singleMuseumStore.museum,
            },
            callback: museumEditCallback,
        });
        modalStore.trigger(modal);
    };
</script>


<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-1 lg:grid-cols-2 m-4 min-h-full">
        <div>
            <header class="card-header text-center font-bold">{$singleMuseumStore.museum?.title}</header>
            <div class="text-center">{$singleMuseumStore.museum?.geo.country.name}, {$singleMuseumStore.museum?.geo?.city}</div>
            {#if $sessionStore.user}
                <button class="btn variant-ghost m-3 mx-auto lg:mr-3 block" type="button" on:click={clickEditButton}>Редактировать</button>
            {/if}
            <div>{$singleMuseumStore.museum?.description}</div>
        </div>
        <img src={$singleMuseumStore.museum?.photo} alt={$singleMuseumStore.museum?.title} class="my-4 mx-auto"/>
    </div>
</article>