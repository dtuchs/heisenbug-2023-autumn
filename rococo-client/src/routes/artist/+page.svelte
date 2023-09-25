<script lang="ts">
	import { Avatar, getModalStore } from '@skeletonlabs/skeleton';
    import NewArtistForm from '$lib/components/NewArtistForm.svelte';
    import type { PageData } from './$types';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import EmptyState from '$lib/components/EmptyState.svelte';
	import EmptySearch from '$lib/components/EmptySearch.svelte';
	import { apiClient } from '$lib/helpers/apiClient';

    export let data: PageData;
    let isSearchNotEmpty = false;
    const modalStore = getModalStore();

    const loadAuthors = async (search: string) => {
        const response = await apiClient.loadArtists(search);

        data.artists = response.content;

        if (search.length > 0) {
            isSearchNotEmpty = true;
        }
	};

    const clickAddButton = () => {
        const modal = prepareModal(
            NewArtistForm, 
            "Новый художник", 
            "Заполните форму, чтобы добавить нового художника");
        modalStore.trigger(modal);
    }
</script>

<ContentPageHeading
    title="Художники"
    searchPlaceholder="Искать художников..."
    addButtonName="Добавить художника"
    onAddButtonClick={clickAddButton}
    loadFunction={loadAuthors}
/>

{#if data?.artists?.length === 0}
    {#if isSearchNotEmpty}
        <EmptySearch
                text="Художники не найдены"
                description="Для указанного вами фильтра мы не смогли найти художников"
        />
    {:else}
        <EmptyState 
            text="Пока что список художников пуст. Чтобы пополнить коллекцию, добавьте нового художника"
            buttonName="Добавить художника"
            onButtonClick={clickAddButton}
        />
    {/if}
	
{:else}
    <section class="grid grid-cols-2 md:grid-cols-6 gap-4 p-8">
        {#each data.artists as artist}
            <a href={`/artist/${artist.id}`} class="flex flex-col justify-center items-center">
                <Avatar src={artist.photo} width="w-48" rounded="rounded-full" />				
                <span class="flex-auto">{artist.name}</span>
            </a>
        {/each}
    </section>
{/if}