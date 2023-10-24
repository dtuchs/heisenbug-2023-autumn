<script lang="ts">
	import { getModalStore } from "@skeletonlabs/skeleton";
	import FormWrapper from "../../FormWrapper.svelte";
	import ModalButtonGroup from "../../ModalButtonGroup.svelte";
	import Input from "../../formElements/Input.svelte";
	import Select from "../../formElements/Select.svelte";
	import ImageInput from "../../formElements/ImageInput.svelte";
	import { apiClient } from "$lib/api/apiClient";
	import Textarea from "../../formElements/Textarea.svelte";
	import { blobToBase64 } from "$lib/helpers/imageUtils";
	import type {IdDto} from "$lib/types/IdDto";
	import {validateImage} from "$lib/helpers/validate";
	import {paintingFormErrorStore} from "$lib/components/forms/painting/painting-form.error.store";
	import {validateForm} from "$lib/components/forms/painting/validate";

	const modalStore = getModalStore();

	export let parent: any;
	let data = $modalStore[0]?.valueAttr as IdDto;

	let files: FileList;
	let title = "";
	let description = "";
	let content = "";
	let authorId = "";
	let museumId: string | undefined = undefined;

	paintingFormErrorStore.set({
		title: "",
		description: "",
		content: "",
		authorId: "",
		museumId: "",
	});

	const onSubmit = async (evt: SubmitEvent) => {
		evt.preventDefault();
		const file = files[0];
		paintingFormErrorStore.update((prevState) => {
			return {
				...prevState,
				photo: validateImage(file),
			}
		});
		validateForm(title, description, data?.id ?? authorId);
		if(!Object.values($paintingFormErrorStore).some(v => v.length > 0)) {
			content = await blobToBase64(file) as string;
			const res = await apiClient.addPainting({
				title,
				description,
				content,
				artist: {
					id: data?.id ?? authorId,
				},
				museum: {
					id: museumId,
				}});

			if($modalStore[0].response) {
				$modalStore[0].response(res);
			}
			modalStore.close();
		} 
	}

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
		<form class="modal-form space-y-4" on:submit={onSubmit}>
			<Input
				label="Название картины"
				name="title"
				placeholder="Введите название картины..."
				bind:value={title}
				error={$paintingFormErrorStore.title}
				required={true}			
			/>
			<ImageInput
				label="Загрузите изображение картины"
				name="content"
				bind:files={files}
				error={$paintingFormErrorStore.content}
				required={true}
			/>
			{#if !data?.id}
				<Select
						label="Укажите автора картины"
						name="authorId"
						loadFunction={apiClient.loadArtists}
						bind:value={authorId}
						keyName="id"
						valueName="name"
						required={true}
						error={$paintingFormErrorStore.authorId}
				/>
			{/if}
			<Textarea
				label="Описание картины"
				name="description"
				bind:value={description} 
				required={true}
				error={$paintingFormErrorStore.description}
			/>
			<Select
				label="Укажите, где хранится оригинал"
				name="museumId"
				loadFunction={apiClient.loadMuseums}
				bind:value={museumId}
				keyName="id"
				valueName="title"
				error={$paintingFormErrorStore.museumId}
			/>	
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}