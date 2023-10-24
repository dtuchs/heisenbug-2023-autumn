<script lang="ts">
	import { getModalStore } from "@skeletonlabs/skeleton";
	import ModalButtonGroup from "../../ModalButtonGroup.svelte";
	import { apiClient } from "$lib/api/apiClient";

	import { blobToBase64 } from "$lib/helpers/imageUtils";
	import FormWrapper from "../../FormWrapper.svelte";
	import Input from "../../formElements/Input.svelte";
	import Textarea from "../../formElements/Textarea.svelte";
	import ImageInput from "../../formElements/ImageInput.svelte";
	import {validateImage} from "$lib/helpers/validate";
	import {artistsFormErrorStore} from "$lib/components/forms/artist/artist-form.error.store";
	import {validateForm} from "$lib/components/forms/artist/validate";

	const modalStore = getModalStore();

	export let parent: any;
	let files: FileList;

	let name = "";
	let biography = "";
	let photo = "";

	artistsFormErrorStore.set({
		name: "",
		biography: "",
		photo: "",
	});

	const onSubmit = async (evt: SubmitEvent) => {
		evt.preventDefault();
		const photoFile = files[0];
		artistsFormErrorStore.update((prevState) => {
			return {
				...prevState,
				photo: validateImage(photoFile),
			}
		});
		photo = await blobToBase64(photoFile) as string;
		validateForm(name, biography);

		if(!Object.values($artistsFormErrorStore).some(v => v.length > 0)) {
			const res = await apiClient.addArtist({name, biography, photo});

			if($modalStore[0].response) {
				$modalStore[0].response(res);
			}
			modalStore.close();
		} 
	}

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle="Новый художник" modalBody="Заполните форму, чтобы добавить нового художника">
		<form class="modal-form space-y-4" on:submit={onSubmit} enctype="multipart/form-data">
			<Input 
				label="Имя" 
				name="name" 
				bind:value={name}
				placeholder="Введите имя художника..."
				error={$artistsFormErrorStore.name}
				required={true}
				/>
			<ImageInput
				label="Изображение художника"
				name="photo"
				bind:files={files} 
				required={true}
				error={$artistsFormErrorStore.photo}
				/>
			<Textarea 
				label="Биография"
				name="biography"
				bind:value={biography}
				placeholder="Биография художника"
				required={true}
				error={$artistsFormErrorStore.biography}
			/>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}