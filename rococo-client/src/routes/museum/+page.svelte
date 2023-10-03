<script lang="ts">
	import type { PageData } from './$types';
	import NewMuseumForm from '$lib/components/forms/museum/NewMuseumForm.svelte';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import { getModalStore } from '@skeletonlabs/skeleton';
	import MuseumList from "$lib/components/MuseumList.svelte";
	import {museumsStore} from "$lib/stores/museum.store";
	import {apiClient} from "$lib/helpers/apiClient";
	import ListWrapper from "$lib/components/ListWrapper.svelte";
	import type {MuseumType} from "$lib/types/Museum";
	import {checkDuplicates} from "$lib/helpers/dataUtils.js";

	export let data: PageData;
	let isSearchNotEmpty = false;
	const modalStore = getModalStore();

	let currentPage = 0;

	museumsStore.set({
		museums: data.museums,
		noMoreData: currentPage === data.totalPages - 1,
		isLoading: false,
		ignoreIds: [],
	});

	const loadMuseums = async(search: string) => {
		museumsStore.update((prevState) => {
			return {
				...prevState,
				isLoading: true,
			}
		});
		const response = await apiClient.loadMuseums({search});
		museumsStore.update((prevState) => {
			return {
				...prevState,
				museums: response.content,
				isLoading: false,
				noMoreData: true,
			}
		});
		isSearchNotEmpty = search.length > 0;
	}

	const loadMore = async () => {
		museumsStore.update((prevState) => {
			return {
				...prevState,
				noMoreData: true,
				isLoading: true,
			}
		});
		const response = await apiClient.loadMuseums({page: ++currentPage});
		const newBatch = checkDuplicates<MuseumType>(response.content, museumsStore, $museumsStore.ignoreIds);
		museumsStore.update((prevState) => {
			return {
				...prevState,
				museums: [
					...prevState.museums,
					...newBatch
				],
				noMoreData: currentPage === response.totalPages -1,
				isLoading: false,
			}
		});
	}

	const museumAddCallback = async (result: MuseumType) => {
		museumsStore.update((prevState) => {
			return {
				...prevState,
				museums: [
					result,
					...prevState.museums,
				],
				ignoreIds: [...prevState.ignoreIds, result.id],
			}
		});
	}

	const clickAddButton = () => {
		const modal = prepareModal({
			ref: NewMuseumForm,
			title: "Новый музей",
			body: "Заполните форму, чтобы добавить новый музей",
			callback: museumAddCallback,
		});
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
<ListWrapper
		emptySearchText="Музеи не найдены"
		emptySearchDescription="Для указанного вами фильтра мы не смогли найти ни одного музея"
		emptyStateTitle="Пока что список музеев пуст. Чтобы пополнить коллекцию, добавьте новый музей"
		emptyStateButtonName="Добавить музей"
		noMoreData={$museumsStore.noMoreData}
		isLoading={$museumsStore.isLoading}
		data={$museumsStore.museums}
		{clickAddButton}
		{isSearchNotEmpty}
		{loadMore}
>
	<MuseumList/>
</ListWrapper>



