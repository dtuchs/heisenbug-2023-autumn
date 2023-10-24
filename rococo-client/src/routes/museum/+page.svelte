<script lang="ts">
	import ToastHandler from "$lib/components/ToastHandler.svelte";
	import {apiClient} from "$lib/api/apiClient";
	import MuseumList from "$lib/components/MuseumList.svelte";
	import {museumsStore} from "$lib/stores/museum.store";
	import CommonPage from "$lib/components/content/CommonPage.svelte";
	import NewMuseumForm from "$lib/components/forms/museum/NewMuseumForm.svelte";


</script>

<ToastHandler let:triggerError let:triggerSuccess>
	<CommonPage
			errorTrigger={triggerError}
			successTrigger={triggerSuccess}
			store={museumsStore}
			loadFunction={apiClient.loadMuseums}
			addButtonName="Добавить музей"
			pageTitle="Музеи"
			searchPlaceholder="Искать музей..."
			formComponent={NewMuseumForm}
			emptySearchText="Музеи не найдены"
			emptySearchDescription="Для указанного вами фильтра мы не смогли не найти ни одного музея"
			emptyStateTitle="Пока что список музеев пуст. Чтобы пополнить коллекцию, добавьте новый музей"
			dataKey="title"
			successMessage="Добавлен музей"
	>
		<MuseumList/>
	</CommonPage>
</ToastHandler>


