<script lang="ts">
	import '../app.postcss';
	import {
		AppShell,
		AppBar,
		Avatar,
		Modal,
		LightSwitch,
		initializeStores,
		Toast,
		getModalStore
	} from '@skeletonlabs/skeleton';
	import {onMount} from "svelte";
	import {apiClient} from "$lib/helpers/apiClient";
	import {sessionStore} from "$lib/stores/sessionStore.js";
	import {clearSession, generateCodeChallenge, generateCodeVerifier, getAuthLink} from "$lib/auth/authUtils";
	import {goto} from "$app/navigation";
	import {prepareModal} from "$lib/helpers/prepareModal";
	import UserForm from "$lib/components/forms/user/UserForm.svelte";
	import {authClient} from "$lib/helpers/authClient";

	initializeStores();
	const modalStore = getModalStore();

	const clickProfileButton = () => {
		const modal = prepareModal({
			ref: UserForm,
			title: "Профиль",
			body: "",
			callback: () => {}});
		modalStore.trigger(modal);
	}

	sessionStore.set({
		user: undefined,
	});

	onMount(async () => {
		if (location.pathname === "/authorized") {
			return;
		}
		const res = await apiClient.loadSession();
		if (!!res.username && res.username !== $sessionStore.user?.username) {
			const res = await apiClient.loadUser();
			sessionStore.update(() => {
				return {
					user: res,
				}
			});
		}
	});

	const onLoginClick = async () => {
		const codeVerifier = generateCodeVerifier();
		sessionStorage.setItem('codeVerifier', codeVerifier);
		const codeChallenge = generateCodeChallenge();
		sessionStorage.setItem('codeChallenge', codeChallenge);

		const link = getAuthLink(codeChallenge);
		await goto(link);
	}

	const onLogoutClick = async() => {
		await authClient.logout();
		clearSession();
	}

</script>

<Modal />
<AppShell>
	<svelte:fragment slot="header">
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end" class="px-6">
			<svelte:fragment slot="lead">
				<h1 class="text-6xl">
					<a href="/">
						Ro<span class="text-primary-500">coco</span>
					</a>
				</h1>
			</svelte:fragment>
			<svelte:fragment slot="trail">
				{#if $sessionStore.user}
					<button type="button" class="btn-icon variant-filled-surface relative" on:click={clickProfileButton}>
						<Avatar src={$sessionStore.user.avatar} width="w-16" rounded="rounded-full" />
					</button>
				{/if}
				<nav class="list-nav">
					<ul class="flex items-baseline">
						<li>
							<a href="/painting">
								Картины
							</a>
						</li>
						<li>
							<a href="/artist">
								Художники
							</a>
						</li>
						<li>
							<a href="/museum">
								Музеи
							</a>
						</li>
					</ul>
				</nav>
				<div>
					<LightSwitch rounded="rounded-full"/>
				</div>
				{#if $sessionStore.user}
					<button type="button" class="btn variant-ghost" on:click={onLogoutClick}>
						Выйти
					</button>

				{:else}
					<button type="button" class="btn variant-filled-primary" on:click={onLoginClick}>
						Войти
					</button>
				{/if}
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<slot />
</AppShell>
<Toast />

