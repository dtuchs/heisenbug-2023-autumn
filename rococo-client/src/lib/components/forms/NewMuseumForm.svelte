<script lang="ts">
	import { getModalStore } from "@skeletonlabs/skeleton";
	import FormWrapper from "../FormWrapper.svelte";
	import ModalButtonGroup from "../ModalButtonGroup.svelte";
	import { onMount } from "svelte";
	import type { CountryType } from "$lib/types/Country";
	const modalStore = getModalStore();

	export let parent: any;
	
	const formData = {
        name: "",
        description: "",
        country: "fr",
        photo: "",
    };

	function onSubmit(): void {
		if($modalStore[0].response) {
			$modalStore[0].response(formData);
		} 
		modalStore.close();
	}

    let countries: CountryType[] = [];

    onMount(async () => {
		const response = await fetch(`/api/country`, {
			method: 'GET',
			headers: {
				'content-type': 'application/json',
			},
		});

        countries = await response.json();
	});

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
		<form class="modal-form space-y-4" on:submit={onSubmit}>
			<label class="label">
				<span>Название музея</span>
				<input class="input" type="text" bind:value={formData.name} placeholder="Введите название музея" />
			</label>
            <label class="label">
				<span>Укажите страну</span>
                <select class="select" size="3" bind:value={formData.country}>
                    {#each countries as country(country.id)}
                        <option value={country.id}>{country.name}</option>
                    {/each}
                </select>
			</label>
            <label class="label">
				<span>Укажите город</span>
				<input class="input" type="text" bind:value={formData.name} placeholder="Введите название музея" />
			</label>
            <label class="label">
				<span>Изображение музея</span>
				<input class="input" type="file" bind:value={formData.photo} />
			</label>
			<label class="label">
				<span>О музее</span>
				<textarea class="textarea" rows="4" bind:value={formData.description} placeholder="Описание музея" />
			</label>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}