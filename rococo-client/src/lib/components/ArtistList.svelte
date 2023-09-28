<script lang="ts">
	import { apiClient } from "$lib/helpers/apiClient";
	import viewport from "$lib/hooks/useViewport";
	import type { ArtistType } from "$lib/types/Artist";
	import { Avatar, ProgressRadial } from "@skeletonlabs/skeleton";
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
    let isLoading = false;

    const loadMore = async () => {
        isLoading = true;
        const response = await apiClient.loadArtists({page: ++currentPage});
        totalPages = response.totalPages;
        newBatch = response.content;
        if(currentPage === totalPages -1) {
            noMoreData = true;
        }
        isLoading = false;
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
    <ul class="w-100">
        <div class="grid grid-cols-2 md:grid-cols-6 gap-4 p-8">
        {#each artists as artist(artist.id)}
            <li>
                <a href={`/artist/${artist.id}`} class="flex flex-col justify-center items-center">
                    <Avatar src={artist.photo} width="w-48" rounded="rounded-full" />				
                    <span class="flex-auto py-4">{artist.name}</span>
                </a>
            </li>
        {/each}
        </div>
        {#if !noMoreData}
            <div use:viewport on:viewportenter={() => loadMore()}>
                {#if isLoading}
                <ProgressRadial 
                    class="mx-auto my-20"
                    stroke={80} 
                    meter="stroke-primary-500" 
                    track="stroke-primary-500/30" 
                    width="w-12"/>
                {/if}
            </div>
        {/if}
    </ul>
{/if}