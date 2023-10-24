<script lang="ts">
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import {artistsStore} from "$lib/stores/artist.store";
    import {apiClient} from "$lib/api/apiClient";
    import NewArtistForm from "$lib/components/forms/artist/NewArtistForm.svelte";
    import CommonPage from "$lib/components/content/CommonPage.svelte";
    import ArtistList from "$lib/components/ArtistList.svelte";

</script>
<ToastHandler let:triggerError let:triggerSuccess>
    <CommonPage
            errorTrigger={triggerError}
            successTrigger={triggerSuccess}
            store={artistsStore}
            loadFunction={apiClient.loadArtists}
            addButtonName="Добавить художника"
            pageTitle="Художники"
            searchPlaceholder="Искать художников..."
            formComponent={NewArtistForm}
            emptySearchText="Художники не найдены"
            emptySearchDescription="Для указанного вами фильтра мы не смогли не найти художников"
            emptyStateTitle="Пока что список художников пуст. Чтобы пополнить коллекцию, добавьте нового художника"
            dataKey="name"
            successMessage="Добавлен художник"
    >
        <ArtistList/>
    </CommonPage>
</ToastHandler>


