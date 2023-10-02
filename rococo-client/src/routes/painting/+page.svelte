<script lang="ts">
	import type { PageData } from './$types';
	import NewPaintingForm from '$lib/components/forms/NewPaintingForm.svelte';
	import { getModalStore } from '@skeletonlabs/skeleton';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import PaintingList from "$lib/components/PaintingList.svelte";
	import {paintingsStore} from "$lib/stores/painting.store.js";
	import {apiClient} from "$lib/helpers/apiClient";
	import ListWrapper from "$lib/components/ListWrapper.svelte";
	
	export let data: PageData;
	let isSearchNotEmpty = false;
	const modalStore = getModalStore();

	let currentPage = 0;

	paintingsStore.set({
		paintings: data.paintings,
		noMoreData: data.currentPage === data.totalPages - 1,
		isLoading: false,
	});

	const loadPaintings = async (search: string) => {
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				isLoading: true,
			}
		});
		const response = await apiClient.loadPaintings({search});
		paintingsStore.set({paintings: response.content, noMoreData: true, isLoading: false});
		isSearchNotEmpty = search.length > 0;
	};

	const loadMore = async () => {
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				isLoading: true,
			}
		});
		const response = await apiClient.loadPaintings({page: ++currentPage});
		const newBatch = response.content;
		paintingsStore.update((prevState) => {
			return {paintings: [
					...prevState.paintings,
					...newBatch
				],
				noMoreData: currentPage === response.totalPages -1,
				isLoading: false,
			}
		});
	}

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
<ListWrapper
		emptySearchText="Картины не найдены"
		emptySearchDescription="Для указанного вами фильтра мы не смогли найти ни одной картины"
		emptyStateTitle="Пока что список картин пуст. Чтобы пополнить галерею, добавьте новую картину"
		emptyStateButtonName="Добавить картину"
		{isSearchNotEmpty}
		noMoreData={$paintingsStore.noMoreData}
		isLoading={$paintingsStore.isLoading}
		data={$paintingsStore.paintings}
		{loadMore}
		{clickAddButton}>
	<PaintingList/>
</ListWrapper>





