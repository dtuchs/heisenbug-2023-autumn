<script lang="ts">
	import {getModalStore} from "@skeletonlabs/skeleton";
	import FormWrapper from "../../FormWrapper.svelte";
	import ModalButtonGroup from "../../ModalButtonGroup.svelte";
	import Input from "$lib/components/formElements/Input.svelte";
	import Textarea from "$lib/components/formElements/Textarea.svelte";
	import ImageInput from "$lib/components/formElements/ImageInput.svelte";
	import {apiClient} from "$lib/api/apiClient";
	import Select from "$lib/components/formElements/Select.svelte";
	import {blobToBase64} from "$lib/helpers/imageUtils";
	import {museumFormErrorStore} from "$lib/components/forms/museum/museum-form.error.store";
	import {validateImage} from "$lib/helpers/validate";
	import {validateForm} from "$lib/components/forms/museum/validate";
	const modalStore = getModalStore();

	export let parent: any;

	let files: FileList;
	let title = "";
	let description = "";
	let photo = "";
	let city = "";
	let countryId: string;

	museumFormErrorStore.set({
		title: "",
		description: "",
		photo: "",
		city: "",
		countryId: "",
	});

	const onSubmit = async (evt: SubmitEvent)=> {
		evt.preventDefault();
		const file = files[0];
		museumFormErrorStore.update((prevState) => {
			return {
				...prevState,
				photo: validateImage(file),
			}
		});
		validateForm(title, description, countryId, city);
		if(!Object.values($museumFormErrorStore).some(v => v.length > 0)) {
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
					error={$museumFormErrorStore.title}
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
					error={$museumFormErrorStore.countryId}
			/>
			<Input
					label="Укажите город"
					name="city"
					bind:value={city}
					error={$museumFormErrorStore.city}
					required={true}
			/>
			<ImageInput
					label="Изображение музея"
					name="photo"
					bind:files={files}
					error={$museumFormErrorStore.photo}
					required={true}
			/>
			<Textarea
					label="О музее"
					name="description"
					bind:value={description}
					required={true}
					error={$museumFormErrorStore.description}
					placeholder="Описание музея"
			/>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}