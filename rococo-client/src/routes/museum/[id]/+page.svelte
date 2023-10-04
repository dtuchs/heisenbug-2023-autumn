<script lang="ts">
	import type { PageData } from './$types';
    import {prepareModal} from "$lib/helpers/prepareModal";
    import {getModalStore} from "@skeletonlabs/skeleton";
    import EditMuseumForm from "$lib/components/forms/museum/EditMuseumForm.svelte";
    import {singleMuseumStore} from "$lib/stores/singleMuseum.store";
    import type {MuseumType} from "$lib/types/Museum";
    import {sessionStore} from "$lib/stores/sessionStore";

    const modalStore = getModalStore();
    export let data: PageData;

    singleMuseumStore.set({
        museum: data.museum,
    });

    const museumEditCallback = async (result: MuseumType) => {
        singleMuseumStore.update(() => {
            return {
                museum: result,
            }
        });
    }

    const clickEditButton = () => {
        const modal = prepareModal({
            ref: EditMuseumForm,
            title: "Редактировать музей",
            body: "",
            valueAttr: {
                museum: $singleMuseumStore.museum,
            },
            callback: museumEditCallback,
        });
        modalStore.trigger(modal);
    };
</script>


<article class="card m-8 p-4 min-h-[90%]">
    <div class="grid grid-cols-2 m-4 min-h-full">
        <div>
            <header class="card-header text-center font-bold">{$singleMuseumStore.museum?.title}</header>
            <div class="text-center">{$singleMuseumStore.museum?.geo.country.name}, {$singleMuseumStore.museum?.geo?.city}</div>
            {#if $sessionStore.user}
                <button class="btn variant-ghost m-3 ml-auto block" type="button" on:click={clickEditButton}>Редактировать</button>
            {/if}
            <div>{$singleMuseumStore.museum?.description}</div>
        </div>
        <img src={$singleMuseumStore.museum?.photo} alt={$singleMuseumStore.museum?.title} class="max-w-xl my-4 mx-auto"/>
    </div>
</article>