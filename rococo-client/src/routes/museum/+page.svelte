<script lang="ts">
	import type { PageData } from './$types';
	import NewMuseumForm from '$lib/components/forms/NewMuseumForm.svelte';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import { getModalStore } from '@skeletonlabs/skeleton';
	import EmptySearch from '$lib/components/EmptySearch.svelte';
	import EmptyState from '$lib/components/EmptyState.svelte';

	export let data: PageData;
	let isSearchNotEmpty = false;
	const modalStore = getModalStore();

	const loadMuseums = async(search: string) => {
		const response = await fetch(`/api/museum?search=${search}`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});

        data.museums = await response.json();
		if (search.length > 0) {
            isSearchNotEmpty = true;
        }
	}

	const clickAddButton = () => {
		const modal = prepareModal(
			NewMuseumForm,
			"Новый музей", 
            "Заполните форму, чтобы добавить новый музей");
        modalStore.trigger(modal);
	};

</script>

<ContentPageHeading
    title="Mузеи"
    searchPlaceholder="Искать музей..."
    addButtonName="Добавить музей"
    onAddButtonClick={clickAddButton}
    loadFunction={loadMuseums}
/>

{#if data?.museums?.length === 0}
    {#if isSearchNotEmpty}
        <EmptySearch
                text="Музеи не найдены"
                description="Для указанного вами фильтра мы не смогли найти ни одного музея"
        />
    {:else}
        <EmptyState 
            text="Пока что список музеев пуст. Чтобы пополнить коллекцию, добавьте новый музей"
            buttonName="Добавить музей"
            onButtonClick={clickAddButton}
        />
    {/if}
	
{:else}
	<section class="grid grid-cols-1 md:grid-cols-2 gap-4 p-4">
		{#each data.museums as museum}
			<a href={`/museum/${museum.id}`}>
				<img class="h-auto max-w-full rounded-lg object-cover w-full h-96" src={museum.photo} alt={museum.name}>
				<div class="text-center">{museum.name}</div>
				<div class="text-center">{museum.city}, {museum.country}</div>
			</a>
		{/each}
	</section>
{/if}


