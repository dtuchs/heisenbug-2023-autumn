<script lang="ts">
	import { getModalStore, getToastStore, type ToastSettings } from "@skeletonlabs/skeleton";
	import FormWrapper from "../FormWrapper.svelte";
	import ModalButtonGroup from "../ModalButtonGroup.svelte";
	import Input from "../formElements/Input.svelte";
	import Select from "../formElements/Select.svelte";
	import ImageInput from "../formElements/ImageInput.svelte";
	import { apiClient } from "$lib/helpers/apiClient";
	import Textarea from "../formElements/Textarea.svelte";
	import { Errors } from "$lib/types/Errors";
	import { blobToBase64 } from "$lib/helpers/imageUtils";
	import type {IdDto} from "$lib/types/IdDto";

	const modalStore = getModalStore();
	const toastStore = getToastStore();

	export let parent: any;
	let data = $modalStore[0]?.valueAttr as IdDto;

	let files: FileList;
	let title = "";
	let description = "";
	let content = "";
	let authorId = "";
	let museumId: string | undefined = undefined;

	const errors: Record<string, string> = {
		title: "",
        description: "",
		content: "",
		authorId: "",
		museumId: "",
	}

	const validateImage = (content: File) => {
		if (content.size > 120_000_000) {
			errors.content = Errors.IMAGE_CONSTRAINT_TOO_BIG;
		}
	}

	const validateForm = () => {	
		errors.title = title?.length < 3 
			? Errors.TITLE_LENGTH_CONSTRAINT_MIN
			: title?.length > 255 
				? Errors.TITLE_LENGTH_CONSTRAINT_MAX
				: "";

		errors.description = description?.length < 11
			? Errors.DESCRIPTION_LENGTH_CONSTRAINT_MIN
			: description?.length > 2000
				? Errors.DESCRIPTION_LENGTH_CONSTRAINT_MAX
				: "";

		errors.authorId = !(authorId || data?.id)? Errors.AUTHOR_CONTRAINT_NOT_EMPTY : "";

		return !Object.values(errors).some(v => v.length > 0);

	}

	const onSubmit = async (evt: SubmitEvent) => {
		evt.preventDefault();
		const file = files[0];
		validateImage(file);
		if(validateForm()) {
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
			const t: ToastSettings = {
				message: `Вы добавили картину: ${title}`,
				background: 'variant-filled-primary',
			};
			toastStore.trigger(t);

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
				error={errors.title}	
				required={true}			
			/>
			<ImageInput
				label="Загрузите изображение картины"
				name="content"
				bind:files={files}
				error={errors.content}
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
						error={errors.authorId}
				/>
			{/if}
			<Textarea
				label="Описание картины"
				name="description"
				bind:value={description} 
				required={true}
				error={errors.description}
			/>
			<Select
				label="Укажите, где хранится оригинал"
				name="museumId"
				loadFunction={apiClient.loadMuseums}
				bind:value={museumId}
				keyName="id"
				valueName="title"
				error={errors.museumId}
			/>	
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}