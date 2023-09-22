<script lang="ts">
	import { getModalStore } from "@skeletonlabs/skeleton";
	import FormWrapper from "./FormWrapper.svelte";
	import ModalButtonGroup from "./ModalButtonGroup.svelte";
	const modalStore = getModalStore();

	export let parent: any;
	
	const formData = {
        name: "",
        biography: "",
        photo: "",
    };

	function onSubmit(): void {
		if($modalStore[0].response) {
			$modalStore[0].response(formData);
		} 
		modalStore.close();
	}

</script>

{#if $modalStore[0]}
	<FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
		<form class="modal-form space-y-4" on:submit={onSubmit}>
			<label class="label">
				<span>Имя</span>
				<input class="input" type="text" bind:value={formData.name} placeholder="Введите имя художника..." />
			</label>
			<label class="label">
				<span>Изображение художника</span>
				<input class="input" type="file" bind:value={formData.photo} />
			</label>
			<label class="label">
				<span>Биография</span>
				<textarea class="textarea" rows="4" bind:value={formData.biography} placeholder="Биография художника" />
			</label>
			<ModalButtonGroup onClose={parent.onClose}/>
		</form>
	</FormWrapper>		
{/if}