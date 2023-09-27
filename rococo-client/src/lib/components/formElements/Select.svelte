<script lang="ts">
	import viewport from "$lib/hooks/useViewport";
	import type { Pageable } from "$lib/types/Pageable";
	import { onMount } from "svelte";

    export let label: string;
    export let value: string | undefined;
    export let error: string;
    export let name: string;
    export let required = false;
    export let keyName = "id";
    export let valueName  = "name";
    export let loadFunction: ({page}: {page?: number}) => Promise<Pageable<[]>>;

    let currentPage = 0;
    let totalPages = 0;
    let noMoreData = false;
    let items:[] = [];
    let newBatch: [] = [];

    $: items = [
		...items,
        ...newBatch,
    ];

    onMount(async() => {
        const res = await loadFunction({page: currentPage});
        items = res.content;
    });

    const loadMore = async () => {
        const response = await loadFunction({page: ++currentPage});
        totalPages = response.totalPages;
        newBatch = response.content;

        if(currentPage === totalPages -1) {
            noMoreData = true;
        }
    }

</script>


<label class="label">
    <span>{label}</span>
    <select 
        class="select" 
        size={3}
        on:scroll
        bind:value={value} 
        on:select={() => error = ""}
        {required}
        {name}
        >
    {#each items as item(item[keyName])}
        {#if item[keyName] === items[items.length - 1][keyName]}
        <option
            use:viewport
            value={item[keyName]}
            on:viewportenter|once={() => {
                if(!noMoreData){
                    loadMore();
                }
            }}>
            {item[valueName]}
        </option>
        {:else}
            <option value={item[keyName]}>{item[valueName]}</option>
        {/if}
    {/each}
    </select>
    <span class="text-error-400">{error}</span>
</label>

