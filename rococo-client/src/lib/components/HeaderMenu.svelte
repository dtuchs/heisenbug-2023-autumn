<script lang="ts">
import {MenuIcon} from "$lib/types/Icon.js";
import {sessionStore} from "$lib/stores/sessionStore.js";
import {Avatar, getModalStore, LightSwitch} from "@skeletonlabs/skeleton";
import PagesNavigation from "$lib/components/PagesNavigation.svelte";
import {
    initLocalStorageAndRedirectToAuth
} from "$lib/api/authUtils";
import {prepareModal} from "$lib/helpers/prepareModal";
import UserForm from "$lib/components/forms/user/UserForm.svelte";
import type {UserType} from "$lib/types/User";

export let errorTrigger: (message: string) => void;
export let successTrigger: (message: string) => void;
export let toggleMenu: () => void;

const modalStore = getModalStore();

const onLoginClick = async () => {
    await initLocalStorageAndRedirectToAuth();
}

const updateProfileCallback = async (result: {
    data?: UserType,
    error?: string,
}) => {
    if(result.error) {
        errorTrigger(result.error);
        return;
    }
    const data = result.data;
    if(data) {
        sessionStore.update((prevState) => {
            return {
                ...prevState,
                user: data,
            }
        });
        successTrigger("Профиль обновлен");
    }
}

const clickProfileButton = () => {
    const modal = prepareModal({
        ref: UserForm,
        title: "Профиль",
        callback: updateProfileCallback,
    });
    modalStore.trigger(modal);
}

</script>

<button type="button" class="block md:hidden shrink-0" on:click={toggleMenu}>
    <img src={MenuIcon} alt="Иконка меню" class="w-50 h-50 md:hidden" width="50" height="50"/>
</button>
<PagesNavigation isBigScreen={true}/>
<div>
    <LightSwitch rounded="rounded-full"/>
</div>
{#if $sessionStore.isLoading}
    <div class="placeholder-circle w-10" />
{:else}
    {#if $sessionStore.user}
        <button type="button" class="btn-icon variant-filled-surface relative" on:click={clickProfileButton}>
            <Avatar src={$sessionStore.user.avatar} width="w-16" rounded="rounded-full" />
        </button>
    {:else}
        <button type="button" class="btn variant-filled-primary" on:click={onLoginClick}>
            Войти
        </button>
    {/if}
{/if}
