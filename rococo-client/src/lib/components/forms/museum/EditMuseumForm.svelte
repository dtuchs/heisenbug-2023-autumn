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
    import type {MuseumType} from "$lib/types/Museum";
    const modalStore = getModalStore();

    export let parent: any;

    let data = $modalStore[0]?.valueAttr as Record<string, MuseumType>;
    let museum = data?.museum as MuseumType;

    let files: FileList;
    let id = museum.id;
    let title = museum.title;
    let description = museum.description;
    let photo = museum.photo;
    let city = museum.geo.city;
    let countryId = museum.geo.country.id;

    museumFormErrorStore.set({
        title: "",
        description: "",
        photo: "",
        city: "",
        countryId: "",
    });

    const onSubmit = async (evt: SubmitEvent)=> {
        evt.preventDefault();
        const file = files?.[0];
        if (file) {
            museumFormErrorStore.update((prevState) => {
                return {
                    ...prevState,
                    photo: validateImage(file),
                }
            });
            photo = await blobToBase64(file) as string;
        }
        validateForm(title, description, countryId, city);
        if(!Object.values($museumFormErrorStore).some(v => v.length > 0)) {
            const res = await apiClient.updateMuseum({
                id,
                title,
                description,
                photo,
                geo: {
                    country: {
                        id: countryId ?? museum.geo.country.id,
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
    <FormWrapper modalTitle="Редактировать музей" modalBody="">
        <form class="modal-form space-y-4" on:submit={onSubmit}>
            <img class="max-w-full rounded-lg object-cover w-full h-80" src={museum.photo} alt={museum.title}>
            <ImageInput
                    label="Изображение музея"
                    name="photo"
                    bind:files={files}
                    error={$museumFormErrorStore.photo}
            />
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
                    error={$museumFormErrorStore.countryId}
            />
            <Input
                    label="Укажите город"
                    name="city"
                    bind:value={city}
                    error={$museumFormErrorStore.city}
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
            <ModalButtonGroup onClose={parent.onClose} submitButtonText="Сохранить"/>
        </form>
    </FormWrapper>
{/if}