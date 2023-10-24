<script lang="ts">
	import ToastHandler from "$lib/components/ToastHandler.svelte";
	import {apiClient} from "$lib/api/apiClient";
	import CommonPage from "$lib/components/content/CommonPage.svelte";
	import PaintingList from "$lib/components/PaintingList.svelte";
	import {paintingsStore} from "$lib/stores/painting.store";
	import NewPaintingForm from "$lib/components/forms/painting/NewPaintingForm.svelte";
</script>

<ToastHandler let:triggerError let:triggerSuccess>
	<CommonPage
			errorTrigger={triggerError}
			successTrigger={triggerSuccess}
			store={paintingsStore}
			loadFunction={apiClient.loadPaintings}
			addButtonName="Добавить картину"
			pageTitle="Картины"
			searchPlaceholder="Искать картины..."
			formComponent={NewPaintingForm}
			emptySearchText="Картины не найдены"
			emptySearchDescription="Для указанного вами фильтра мы не смогли не найти ни одной картины"
			emptyStateTitle="Пока что список картин пуст. Чтобы пополнить коллекцию, добавьте новую картину"
			dataKey="title"
			successMessage="Добавлена картины"
	>
		<PaintingList paintings={$paintingsStore.data}/>
	</CommonPage>
</ToastHandler>





