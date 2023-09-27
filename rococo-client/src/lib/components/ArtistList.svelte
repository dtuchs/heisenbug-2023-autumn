<script lang="ts">
	import { apiClient } from "$lib/helpers/apiClient";
	import viewport from "$lib/hooks/useViewport";
	import type { ArtistType } from "$lib/types/Artist";
	import { Avatar } from "@skeletonlabs/skeleton";
	import EmptySearch from "./EmptySearch.svelte";
	import EmptyState from "./EmptyState.svelte";

    export let artists: ArtistType[] = [];
    export let clickAddButton: () => void;
    export let isSearchNotEmpty = false;
	let newBatch: ArtistType[] = [];

    $: artists = [
		...artists,
        ...newBatch
    ];

    let currentPage = 0;
    let totalPages = 0;
    let noMoreData = false;

    const loadMore = async () => {
        const response = await apiClient.loadArtists({page: ++currentPage});
        totalPages = response.totalPages;
        newBatch = response.content;

        if(currentPage === totalPages -1) {
            noMoreData = true;
        }
    }

</script>

{#if artists.length === 0}
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
    <ul class="grid grid-cols-2 md:grid-cols-6 gap-4 p-8">
        {#each artists as artist(artist.id)}
            <li>
                <a href={`/artist/${artist.id}`} class="flex flex-col justify-center items-center">
                    <Avatar src={artist.photo} width="w-48" rounded="rounded-full" />				
                    <span class="flex-auto">{artist.name}</span>
                </a>
            </li>
        {/each}
        {#if !noMoreData}
            <div  
                use:viewport
                on:viewportenter={() => loadMore()}>
            </div>
        {/if}
    </ul>
{/if}