<script lang="ts">
    import {Avatar, getModalStore} from "@skeletonlabs/skeleton";
    import ModalButtonGroup from "../../ModalButtonGroup.svelte";
    import { apiClient } from "$lib/api/apiClient";
    import { blobToBase64 } from "$lib/helpers/imageUtils";
    import FormWrapper from "../../FormWrapper.svelte";
    import Input from "../../formElements/Input.svelte";
    import Textarea from "../../formElements/Textarea.svelte";
    import ImageInput from "../../formElements/ImageInput.svelte";
    import {validateForm} from "$lib/components/forms/artist/validate";
    import {validateImage} from "$lib/helpers/validate";
    import {artistsFormErrorStore} from "$lib/components/forms/artist/artist-form.error.store";
    import type {ArtistType} from "$lib/types/Artist";

    const modalStore = getModalStore();

    export let parent: any;
    let files: FileList;

    let data = $modalStore[0]?.valueAttr as ArtistType;

    let id = data.id;
    let name = data.name;
    let biography = data.biography;
    let photo = data.photo;

    artistsFormErrorStore.set({
        name: "",
        biography: "",
        photo: "",
    });
    const onSubmit = async (evt: SubmitEvent) => {
        evt.preventDefault();
        const photoFile = files?.[0];
        if (photoFile) {
            artistsFormErrorStore.update((prevState) => {
                return {
                    ...prevState,
                    photo: validateImage(photoFile),
                }
            });
            photo = await blobToBase64(photoFile) as string;
        }
        validateForm(name, biography);
        if(!Object.values($artistsFormErrorStore).some(v => v.length > 0)) {
            const res = await apiClient.updateArtist({id, name, biography, photo});
            if($modalStore[0].response) {
                $modalStore[0].response(res);
            }
            modalStore.close();
        }
    }
</script>

{#if $modalStore[0]}
    <FormWrapper modalTitle="Редактировать художника" modalBody="">
        <form class="modal-form space-y-4" on:submit={onSubmit} enctype="multipart/form-data">
            <Avatar src={photo} width="w-48" rounded="rounded-full" class="mx-auto"/>
            <ImageInput
                    label="Обновить изображение художника"
                    name="photo"
                    bind:files={files}
                    error={$artistsFormErrorStore.photo}
            />
            <Input
                    label="Имя"
                    name="name"
                    bind:value={name}
                    placeholder="Введите имя художника..."
                    error={$artistsFormErrorStore.name}
                    required={true}
            />
            <Textarea
                    label="Биография"
                    name="biography"
                    bind:value={biography}
                    placeholder="Биография художника"
                    required={true}
                    error={$artistsFormErrorStore.biography}
            />
            <ModalButtonGroup onClose={parent.onClose} submitButtonText="Сохранить"/>
        </form>
    </FormWrapper>
{/if}