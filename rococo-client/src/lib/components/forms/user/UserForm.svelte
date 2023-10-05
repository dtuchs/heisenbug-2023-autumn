<script lang="ts">
    import {Avatar, getModalStore, getToastStore} from "@skeletonlabs/skeleton";
    import type {ToastSettings} from "@skeletonlabs/skeleton";
    import FormWrapper from "$lib/components/FormWrapper.svelte";
    import Input from "$lib/components/formElements/Input.svelte";
    import {sessionStore} from "$lib/stores/sessionStore";
    import {userFormErrorStore} from "$lib/components/forms/user/user-form.error.store";
    import ImageInput from "$lib/components/formElements/ImageInput.svelte";
    import {artistsFormErrorStore} from "$lib/components/forms/artist/artist-form.error.store";
    import {validateImage} from "$lib/helpers/validate";
    import {blobToBase64} from "$lib/helpers/imageUtils";
    import {validateForm} from "$lib/components/forms/user/validate";
    import {apiClient} from "$lib/helpers/apiClient";

    const modalStore = getModalStore();
    const toastStore = getToastStore();

    let files: FileList;

    let id = $sessionStore.user?.id;
    let firstname = $sessionStore.user?.firstname ?? "";
    let lastname = $sessionStore.user?.lastname ?? "";
    let username = $sessionStore.user?.username;
    let avatar = $sessionStore.user?.avatar;

    userFormErrorStore.set({
        firstname: "",
        lastname: "",
        avatar: "",
    });

    const onSubmit = async (evt: SubmitEvent) => {
        evt.preventDefault();
        const file = files?.[0];
        if(file) {
            artistsFormErrorStore.update((prevState) => {
                return {
                    ...prevState,
                    photo: validateImage(file),
                }
            });
            avatar = await blobToBase64(file) as string;
        }
        validateForm(firstname, lastname);
        if(!Object.values($userFormErrorStore).some(v => v.length > 0) && id && username) {
            const res = await apiClient.updateUser({
                id,
                username,
                firstname,
                lastname,
                avatar,
                });
            const t: ToastSettings = {
                message: `Вы обновили свой профиль`,
                background: 'variant-filled-primary',
            };
            toastStore.trigger(t);

            sessionStore.update(() => {
                return {
                    user: res,
                }
            });

            modalStore.close();
        }
    }

</script>

{#if $modalStore[0] && id}
    <FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
        <form class="modal-form space-y-4" on:submit={onSubmit}>
            <Avatar class="mx-auto" src={avatar} width="w-32" rounded="rounded-full" />
            <ImageInput
                    label="Обновить фото профиля"
                    name="content"
                    bind:files={files}
                    error={$userFormErrorStore.avatar}
            />
            <h4 class="text-center">@{username}</h4>
            <Input
                    label="Имя"
                    name="firstname"
                    placeholder="Ваше имя..."
                    bind:value={firstname}
                    error={$userFormErrorStore.firstname}
            />
            <Input
                    label="Фамилия"
                    name="surname"
                    placeholder="Ваша фамилия..."
                    bind:value={lastname}
                    error={$userFormErrorStore.lastname}
            />
            <div class="text-center">
                <button type="submit" class="btn variant-filled-primary m-2">Обновить профиль</button>
            </div>
        </form>
    </FormWrapper>
{/if}