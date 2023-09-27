<script lang="ts">
	import { getModalStore, getToastStore, type ToastSettings } from "@skeletonlabs/skeleton";
	import ModalButtonGroup from "../ModalButtonGroup.svelte";
	import { apiClient } from "$lib/helpers/apiClient";

	import { blobToBase64 } from "$lib/helpers/imageUtils";
	import FormWrapper from "../FormWrapper.svelte";
	import Input from "../formElements/Input.svelte";
	import Textarea from "../formElements/Textarea.svelte";
	import { Errors } from "$lib/types/Errors";
	import ImageInput from "../formElements/ImageInput.svelte";

	const modalStore = getModalStore();
	const toastStore = getToastStore();

	export let parent: any;
	let files: FileList;

	let name = "";
	let biography = "";
	let photo = "";

	const errors: Record<string, string> = {
		name: "",
        biography: "",
		photo: "",
	}
	
	const validateImage = (photoFile: File) => {
		if (photoFile.size > 120_000_000) {
			errors.photo = Errors.IMAGE_CONSTRAINT_TOO_BIG;
		}
	}

	const validateForm = () => {	
		errors.name = name?.length < 3 
			? Errors.NAME_LENGTH_CONSTRAINT_MIN
			: name?.length > 255 
				? Errors.NAME_LENGTH_CONSTRAINT_MAX
				: "";

		errors.biography = biography?.length < 11
			? Errors.BIOGRAPHY_LENGTH_CONSTRAINT_MIN
			: biography?.length > 2000
				? Errors.BIOGRAPHY_LENGTH_CONSTRAINT_MAX
				: "";

		if(Object.values(errors).some(v => v.length > 0)) {
			return false;
		}
		return true;
	}

	const onSubmit = async (evt: SubmitEvent) => {
		evt.preventDefault();
		const photoFile = files[0];
		validateImage(photoFile);
		photo = await blobToBase64(photoFile) as string;
		if(validateForm()) {
			const res = await apiClient.addArtist({name, biography, photo});
			if(res.ok) {
				const t: ToastSettings = {
					message: `Вы добавили художника: ${name}`,
					background: 'variant-filled-primary',
				};
				toastStore.trigger(t);
			}
			if($modalStore[0].response) {
				$modalStore[0].response(res);
			} 
			modalStore.close();
		} 
	}

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
		<form class="modal-form space-y-4" on:submit={onSubmit} enctype="multipart/form-data">
			<Input 
				label="Имя" 
				name="name" 
				bind:value={name}
				placeholder="Введите имя художника..."
				error={errors.name}
				required={true}
				/>
			<ImageInput
				label="Изображение художника"
				name="photo"
				bind:files={files} 
				required={true}
				error={errors.photo}
				/>
			<Textarea 
				label="Биография"
				name="biography"
				bind:value={biography}
				placeholder="Биография художника"
				required={true}
				error={errors.biography}
			/>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}