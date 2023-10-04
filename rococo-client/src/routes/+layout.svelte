<script lang="ts">
	import '../app.postcss';
	import { AppShell, AppBar, Avatar, Modal, popup, LightSwitch, initializeStores, Toast } from '@skeletonlabs/skeleton';
	import type { PopupSettings } from '@skeletonlabs/skeleton';

	// Floating UI for Popups
	import { computePosition, autoUpdate, flip, shift, offset, arrow } from '@floating-ui/dom';
	import { storePopup } from '@skeletonlabs/skeleton';
	import type { LayoutServerData} from './$types';
	import {onMount} from "svelte";
	import {apiClient} from "$lib/helpers/apiClient";
	import {sessionStore} from "$lib/stores/sessionStore.js";
	import {generateCodeChallenge, generateCodeVerifier} from "$lib/auth/authUtils";
	import {goto} from "$app/navigation";

	initializeStores();
	storePopup.set({ computePosition, autoUpdate, flip, shift, offset, arrow });

	const popupFeatured: PopupSettings = {
		event: "click",
		target: "popup",
		placement: "bottom",
	};

	sessionStore.set({
		user: undefined,
	});

	onMount(async () => {
		if (location.pathname === "/authorized") {
			return;
		}
		const res = await apiClient.loadSession();
		if (res.username) {
			const res = await apiClient.loadUser();
			sessionStore.update(() => {
				return {
					user: res,
				}
			});
		}
	});

	export let data: LayoutServerData | undefined;

	const onLoginClick = async () => {
		const codeVerifier = generateCodeVerifier();
		sessionStorage.setItem('codeVerifier', codeVerifier);
		const codeChallenge = generateCodeChallenge();
		sessionStorage.setItem('codeChallenge', codeChallenge);

		const link = `http://127.0.0.1:9000/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=${codeChallenge}&code_challenge_method=S256`;
		await goto(link);
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
				<LightSwitch rounded="rounded-full"/>
				{#if $sessionStore.user}
					<button type="button" class="btn-icon variant-filled-surface relative" use:popup={popupFeatured}>
						<Avatar src="https://images.unsplash.com/photo-1617296538902-887900d9b592?ixid=M3w0Njc5ODF8MHwxfGFsbHx8fHx8fHx8fDE2ODc5NzExMDB8&ixlib=rb-4.0.3&w=128&h=128&auto=format&fit=crop" width="w-16" rounded="rounded-full" />				
					</button>
				{:else}
					<button type="button" class="btn variant-filled-primary" on:click={onLoginClick}>
						Войти				
					</button>
				{/if}		
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<div class="card m-4 p-4 w-72 shadow-xl" data-popup="popup">
		<form class="flex flex-col justify-center items-center">
			<Avatar src="https://images.unsplash.com/photo-1617296538902-887900d9b592?ixid=M3w0Njc5ODF8MHwxfGFsbHx8fHx8fHx8fDE2ODc5NzExMDB8&ixlib=rb-4.0.3&w=128&h=128&auto=format&fit=crop" width="w-32" rounded="rounded-full" />				
			<h4>@renoir</h4>
			<label class="label m-2">
				<span>Имя</span>
				<input class="input" type="text" placeholder="Имя" />
			</label>
			<label class="label m-2">
				<span>Фамилия</span>
				<input class="input" type="text" placeholder="Фамилия" />
			</label>
			<label class="label m-2">
				<span>Email</span>
				<input class="input" type="text" placeholder="Email" />
			</label>
			<button type="button" class="btn variant-filled-primary m-2">Обновить профиль</button>
		</form>
	</div>
	<slot />
</AppShell>
<Toast />

