<script lang="ts">
	import type { PageData } from './$types';
	import { SearchIcon } from '$lib/Icon';

	export let data: PageData;
	let search = "";

	async function loadMuseums() {
		const response = await fetch(`/api/museum?search=${search}`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});

        data.museums = await response.json();
	}

</script>


<div class="flex items-center justify-between m-4">
    <h2 class="text-3xl m-4">Музеи</h2>
    <button type="button" class="btn variant-filled-primary ml-4">Добавить музей</button>
</div>

<div class="flex justify-center mb-4 mx-2">
    <input class="input" bind:value={search} title="Искать картину..." type="search" placeholder="Искать картину по названию..." on:keypress={(evt) => {
        if(evt.key === "Enter") {
            loadMuseums();
        }
    }}/>
    <button type="button" class="btn-icon variant-filled-surface ml-4" on:click={loadMuseums}>
        <img src={SearchIcon} alt="Иконка поиска"/>
    </button>
</div>

<section class="grid grid-cols-1 md:grid-cols-2 gap-4 p-4">
	{#each data.museums as museum}
		<a href={`/museum/${museum.id}`}>
			<img class="h-auto max-w-full rounded-lg object-cover w-full h-96" src={museum.photo} alt={museum.name}>
			<div class="text-center">{museum.name}</div>
            <div class="text-center">{museum.city}, {museum.country}</div>
		</a>
	{/each}
</section>


