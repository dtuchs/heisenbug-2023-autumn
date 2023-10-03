<script lang="ts">
	import {getModalStore, getToastStore} from "@skeletonlabs/skeleton";
	import type {ToastSettings} from "@skeletonlabs/skeleton";
	import FormWrapper from "../../FormWrapper.svelte";
	import ModalButtonGroup from "../../ModalButtonGroup.svelte";
	import Input from "$lib/components/formElements/Input.svelte";
	import Textarea from "$lib/components/formElements/Textarea.svelte";
	import ImageInput from "$lib/components/formElements/ImageInput.svelte";
	import {apiClient} from "$lib/helpers/apiClient";
	import Select from "$lib/components/formElements/Select.svelte";
	import {Errors} from "$lib/types/Errors";
	import {blobToBase64} from "$lib/helpers/imageUtils";
	const modalStore = getModalStore();
	const toastStore = getToastStore();

	export let parent: any;

	let files: FileList;
	let title = "";
	let description = "";
	let photo = "";
	let city = "";
	let countryId: string;

	const errors: Record<string, string> = {
		title: "",
		description: "",
		photo: "",
		city: "",
		countryId: "",
	}

	const validateImage = (content: File) => {
		if (content.size > 120_000_000) {
			errors.photo = Errors.IMAGE_CONSTRAINT_TOO_BIG;
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

		errors.authorId = !countryId ? Errors.COUNTRY_CONTRAINT_NOT_EMPTY : "";

		errors.city = city?.length < 3
				? Errors.DESCRIPTION_LENGTH_CONSTRAINT_MIN
				: description?.length > 255
						? Errors.DESCRIPTION_LENGTH_CONSTRAINT_MAX
						: "";

		return !Object.values(errors).some(v => v.length > 0);
	}

	const onSubmit = async (evt: SubmitEvent)=> {
		evt.preventDefault();
		const file = files[0];
		validateImage(file);
		if(validateForm()) {
			photo = await blobToBase64(file) as string;
			const res = await apiClient.addMuseum({
				title,
				description,
				photo,
				geo: {
					country: {
						id: countryId
					},
					city,
				}
			});
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
					label="Название музея"
					name="title"
					placeholder="Введите название музея..."
					bind:value={title}
					error={errors.title}
					required={true}
			/>
			<Select
					label="Укажите страну"
					name="countryId"
					loadFunction={apiClient.loadCountries}
					bind:value={countryId}
					keyName="id"
					valueName="name"
					required={true}
					error={errors.countryId}
			/>
			<Input
					label="Укажите город"
					name="city"
					bind:value={city}
					error={errors.city}
					required={true}
			/>
			<ImageInput
					label="Изображение музея"
					name="photo"
					bind:files={files}
					error={errors.photo}
					required={true}
			/>
			<Textarea
					label="О музее"
					name="description"
					bind:value={description}
					required={true}
					error={errors.description}
					placeholder="Описание музея"
			/>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}