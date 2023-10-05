<script lang="ts">
    import {Avatar, getModalStore, getToastStore} from "@skeletonlabs/skeleton";
    import FormWrapper from "$lib/components/FormWrapper.svelte";
    import Input from "$lib/components/formElements/Input.svelte";
    import {sessionStore} from "$lib/stores/sessionStore";
    import {userFormErrorStore} from "$lib/components/forms/user/user-form.error.store";
    import ImageInput from "$lib/components/formElements/ImageInput.svelte";
    import {paintingFormErrorStore} from "$lib/components/forms/painting/painting-form.error.store";

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

</script>

{#if $modalStore[0]}
    <FormWrapper modalTitle={$modalStore[0].title ?? ""} modalBody={$modalStore[0].body ?? ""}>
        <form class="modal-form space-y-4">
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
                <button type="button" class="btn variant-filled-primary m-2">Обновить профиль</button>
            </div>
        </form>
    </FormWrapper>
{/if}