<script lang="ts">
	import type { PageData } from './$types';
	import NewPaintingForm from '$lib/components/forms/NewPaintingForm.svelte';
	import { getModalStore } from '@skeletonlabs/skeleton';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import EmptyState from '$lib/components/EmptyState.svelte';
	import EmptySearch from '$lib/components/EmptySearch.svelte';
	
	export let data: PageData;
	let isSearchNotEmpty = false;
	const modalStore = getModalStore();

	const loadPaintings = async (search: string) => {
		const response = await fetch(`/api/painting?search=${search}`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});
        data.paintings = await response.json();
		if (search.length > 0) {
            isSearchNotEmpty = true;
        }
	};

	const clickAddButton = () => {
		const modal = prepareModal(
			NewPaintingForm,
			"Новая картина", 
            "Заполните форму, чтобы добавить новую картину");
        modalStore.trigger(modal);
	};

</script>

<ContentPageHeading
    title="Картины"
    searchPlaceholder="Искать картину..."
    addButtonName="Добавить картину"
    onAddButtonClick={clickAddButton}
    loadFunction={loadPaintings}
/>


{#if data?.paintings?.length === 0}
	{#if isSearchNotEmpty}
        <EmptySearch
                text="Картины не найдены"
                description="Для указанного вами фильтра мы не смогли найти ни одной картины"
        />
    {:else}
		<EmptyState 
			text="Пока что список картин пуст. Чтобы пополнить галерею, добавьте новую картину"
			buttonName="Добавить картину"
			onButtonClick={clickAddButton}
		/>
    {/if}
	
{:else}
	<section class="grid grid-cols-2 md:grid-cols-3 gap-4 p-4">
		{#each data.paintings as painting}
			<a href={`/painting/${painting?.id}`}>
				<img class="h-auto max-w-full rounded-lg object-cover w-full h-80" src={painting.src} alt={painting.title}>
				<div class="text-center">{painting?.title}, {painting?.year}</div>
			</a>
		{/each}
	</section>
{/if}




