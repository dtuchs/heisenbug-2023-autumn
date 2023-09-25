<script lang="ts">
	import { getModalStore } from "@skeletonlabs/skeleton";
	import FormWrapper from "./FormWrapper.svelte";
	import ModalButtonGroup from "./ModalButtonGroup.svelte";
	import { apiClient } from "$lib/helpers/apiClient";
	import type { NewArtistType } from "$lib/types/Artist";
	import type { File } from "buffer";
	const modalStore = getModalStore();

	export let parent: any;
	const authorizedExtensions = ['.jpg', '.jpeg', '.png', '.webp'];
	let photo: FileList;

	
	const formData: NewArtistType = {
        name: "",
        biography: "",
		photo: undefined,
    };
	
	const validate = () => {
		if(!photo?.[0]) {
			console.log("No fole uploaded");
		} else {
			const result = photo[0];
			if(result.size > 1020) {
				console.log("Too big image");
			}
			formData.photo = result;
		}

	}

	const onSubmit = async () => {
		validate();
		console.log(formData.photo);

		let data = new FormData();
		data.append("name", formData.name);
		data.append("biography", formData.biography);
		data.append("photo", formData.photo);

		const res = await apiClient.addArtist(data);

		if($modalStore[0].response) {
			$modalStore[0].response(res);
		} 
		modalStore.close();
	}

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
		<form class="modal-form space-y-4" on:submit={onSubmit} enctype="multipart/form-data">
			<label class="label">
				<span>Имя</span>
				<input 
					name="name"
					class="input" 
					type="text" 
					bind:value={formData.name} 
					placeholder="Введите имя художника..." />
			</label>
			<label class="label">
				<span>Изображение художника</span>
				<input
					name="photo"
					class="input" 
					type="file" 
					accept={authorizedExtensions.join(',')}
					bind:files={photo} 
					/>
			</label>
			<label class="label">
				<span>Биография</span>
				<textarea 
					name="biography"
					class="textarea" 
					rows="4" 
					bind:value={formData.biography} 
					placeholder="Биография художника" />
			</label>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}