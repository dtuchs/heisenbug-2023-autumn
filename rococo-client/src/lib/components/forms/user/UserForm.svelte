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
    import {apiClient} from "$lib/api/apiClient";
    import {authClient} from "$lib/api/authClient";
    import {clearSession} from "$lib/api/authUtils";
    import ModalButtonGroup from "$lib/components/ModalButtonGroup.svelte";

    const modalStore = getModalStore();
    const toastStore = getToastStore();

    export let parent: any;

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
            if($modalStore[0].response) {
                $modalStore[0].response(res);
            }
            modalStore.close();
        }
    }

    const onLogoutClick = async() => {
        await authClient.logout();
        clearSession();
        modalStore.close();
        const t: ToastSettings = {
            message: `Сессия завершена`,
            background: 'variant-filled-tertiary',
        };
        toastStore.trigger(t);
    }

</script>

{#if $modalStore[0] && id}
    <FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
        <form class="modal-form space-y-4 relative" on:submit={onSubmit}>
            <div class="text-right absolute right-0">
                <button type="button" class="btn variant-ghost" on:click={onLogoutClick}>
                    Выйти
                </button>
            </div>
            <Avatar class="mx-auto" src={avatar} width="w-48" rounded="rounded-full" />
            <h4 class="text-center">@{username}</h4>
            <ImageInput
                    label="Обновить фото профиля"
                    name="content"
                    bind:files={files}
                    error={$userFormErrorStore.avatar}
            />
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
                <ModalButtonGroup onClose={parent.onClose} submitButtonText="Обновить профиль"/>
            </div>
        </form>
    </FormWrapper>
{/if}