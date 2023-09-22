<script lang="ts">
	import { getModalStore } from "@skeletonlabs/skeleton";
	import FormWrapper from "./FormWrapper.svelte";
	import ModalButtonGroup from "./ModalButtonGroup.svelte";
	import type { ArtistType } from "$lib/types/Artist";
	import { onMount } from "svelte";

	export let parent: any;
	
	const formData = {
        title: "",
        description: "",
        year: undefined,
        src: "",
        authorId: 1,
    };

    const modalStore = getModalStore();
	function onSubmit(): void {
		if($modalStore[0].response) {
			$modalStore[0].response(formData);
		} 
		modalStore.close();
	}

    let artists: ArtistType[] = []; 
	onMount(async () => {
		const response = await fetch(`/api/artist`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});

        artists = await response.json();
	});

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
		<form class="modal-form space-y-4" on:submit={onSubmit}>
			<label class="label">
				<span>Название картины</span>
				<input class="input" type="text" bind:value={formData.title} placeholder="Введите название картины..." />
			</label>
			<label class="label">
				<span>Загрузите изображение картины</span>
				<input class="input" type="file" bind:value={formData.src} />
			</label>
            <label class="label">
				<span>Укажите автора картины</span>
                <select class="select" size="3" bind:value={formData.authorId}>
                    {#each artists as artist(artist.id)}
                        <option value={artist.id}>{artist.name}</option>
                    {/each}
                </select>
			</label>
			<label class="label">
				<span>Описание картины</span>
				<textarea class="textarea" rows="4" bind:value={formData.description} placeholder="Описание картины" />
			</label>
            <label class="label">
				<span>Год создания</span>
				<input class="input" type="number" bind:value={formData.year} placeholder="Укажите год создания картины..." />
			</label>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}