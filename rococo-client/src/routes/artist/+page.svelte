<script lang="ts">
	import { Avatar, getModalStore } from '@skeletonlabs/skeleton';
    import NewArtistForm from '$lib/components/NewArtistForm.svelte';
    import type { PageData } from './$types';
	import ContentPageHeading from '$lib/components/ContentPageHeading.svelte';
	import { prepareModal } from '$lib/helpers/prepareModal';

    export let data: PageData;
    const modalStore = getModalStore();

    async function loadAuthors(search: string) {
		const response = await fetch(`/api/artist?search=${search}`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});
        data.artists = await response.json();
	}

</script>

<ContentPageHeading
    title="Художники"
    searchPlaceholder="Искать художников..."
    addButtonName="Добавить художника"
    onAddButtonClick={() => {
        const modal = prepareModal(NewArtistForm);
        modalStore.trigger(modal);
    }}
    loadFunction={loadAuthors}
/>

<section class="grid grid-cols-2 md:grid-cols-6 gap-4 p-8">
    {#each data.artists as artist}
        <a href={`/artist/${artist.id}`} class="flex flex-col justify-center items-center">
            <Avatar src={artist.photo} width="w-48" rounded="rounded-full" />				
            <span class="flex-auto">{artist.name}</span>
        </a>
    {/each}
</section>