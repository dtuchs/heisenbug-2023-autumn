<script lang="ts">
	import { getModalStore } from '@skeletonlabs/skeleton';
    import NewArtistForm from '$lib/components/forms/NewArtistForm.svelte';
    import type { PageData } from './$types';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';
	import { apiClient } from '$lib/helpers/apiClient';
	import ArtistList from '$lib/components/ArtistList.svelte';

    export let data: PageData;
    let isSearchNotEmpty = false;
    const modalStore = getModalStore();

    const loadAuthors = async (search: string) => {
        const response = await apiClient.loadArtists({search});
        data.artists = response.content;
      
        if (search.length > 0) {
            isSearchNotEmpty = true;
        }
	};
 
    const clickAddButton = () => {
        const modal = prepareModal(
            NewArtistForm, 
            "Новый художник", 
            "Заполните форму, чтобы добавить нового художника");
        modalStore.trigger(modal);
    }
</script>

<ContentPageHeading
    title="Художники"
    searchPlaceholder="Искать художников..."
    addButtonName="Добавить художника"
    onAddButtonClick={clickAddButton}
    loadFunction={loadAuthors}
/>
<ArtistList
    {clickAddButton}
    artists={data.artists}
    {isSearchNotEmpty}
/>
