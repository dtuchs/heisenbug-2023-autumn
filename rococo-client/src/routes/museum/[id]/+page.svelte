<script lang="ts">
	import type { PageData } from './$types';
	import Map from '$lib/components/Map.svelte';

    export let data: PageData;
</script>


<article class="card m-8 p-4">
    <header class="card-header text-center font-bold">{data?.museum?.name}</header>
    <div class="text-center">{data?.museum?.country}, {data?.museum?.city}</div>
    <div class="grid grid-cols-2">
        <img src={data?.museum?.photo} alt={data?.museum?.name} class="max-w-xl my-4 mx-auto"/>
        {#if data?.museum?.location}
            <Map coords={data?.museum?.location}/>
        {/if}
    </div>
    <div>{data?.museum?.description}</div>
</article>