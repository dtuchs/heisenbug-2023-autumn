<script lang="ts">
	import type { PageData } from './$types';
	import NewPaintingForm from '$lib/components/forms/painting/NewPaintingForm.svelte';
	import { getModalStore } from '@skeletonlabs/skeleton';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import PaintingList from "$lib/components/PaintingList.svelte";
	import {paintingsStore} from "$lib/stores/painting.store.js";
	import {apiClient} from "$lib/helpers/apiClient";
	import ListWrapper from "$lib/components/ListWrapper.svelte";
	import type {PaintingType} from "$lib/types/Painting";
	import {checkDuplicates} from "$lib/helpers/dataUtils";
	
	export let data: PageData;
	let isSearchNotEmpty = false;
	const modalStore = getModalStore();

	let currentPage = 0;

	paintingsStore.set({
		paintings: data.paintings,
		noMoreData: currentPage === data.totalPages - 1,
		isLoading: false,
		ignoreIds: [],
	});

	const loadPaintings = async (search: string) => {
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				isLoading: true,
			}
		});
		const response = await apiClient.loadPaintings({search});
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				paintings: response.content,
				isLoading: false,
				noMoreData: true,
			}
		});
		isSearchNotEmpty = search.length > 0;
	};

	const loadMore = async () => {
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				isLoading: true,
				noMoreData: true,
			}
		});
		const response = await apiClient.loadPaintings({page: ++currentPage});
		const newBatch = checkDuplicates<PaintingType>(response.content, paintingsStore, $paintingsStore.ignoreIds);
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				paintings: [
					...prevState.paintings,
					...newBatch
				],
				noMoreData: currentPage === response.totalPages -1,
				isLoading: false,
			}
		});
	}

	const paintingAddCallback = async (result: PaintingType) => {
		paintingsStore.update((prevState) => {
			return {
				...prevState,
				paintings: [
					result,
					...prevState.paintings,
				],
				ignoreIds: [...prevState.ignoreIds, result.id],
			}
		});
	}

	const clickAddButton = () => {
		const modal = prepareModal({
			ref: NewPaintingForm,
			title: "Новая картина",
			body: "Заполните форму, чтобы добавить новую картину",
			callback: paintingAddCallback,
		});
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
	<PaintingList paintings={$paintingsStore.paintings}/>
</ListWrapper>





