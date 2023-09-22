<script lang="ts">
	import type { PageData } from './$types';
	import NewPaintingForm from '$lib/components/NewPaintingForm.svelte';
	import { getModalStore } from '@skeletonlabs/skeleton';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';

	export let data: PageData;
	const modalStore = getModalStore();

	async function loadPaintings(search: string) {
		const response = await fetch(`/api/painting?search=${search}`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});
        data.paintings = await response.json();
	}

</script>

<ContentPageHeading
    title="Картины"
    searchPlaceholder="Искать картину..."
    addButtonName="Добавить картину"
    onAddButtonClick={() => {
		const modal = prepareModal(NewPaintingForm);
        modalStore.trigger(modal);
	}}
    loadFunction={loadPaintings}
/>

<section class="grid grid-cols-2 md:grid-cols-3 gap-4 p-4">
	{#each data.paintings as painting}
		<a href={`/painting/${painting.id}`}>
			<img class="h-auto max-w-full rounded-lg object-cover w-full h-80" src={painting.src} alt={painting.title}>
			<div class="text-center">{painting.title}, {painting.year}</div>
		</a>
	{/each}
</section>


