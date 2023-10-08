<script lang="ts">

    import {onMount} from "svelte";
    import {authClient} from "$lib/helpers/authClient";
    import {apiClient} from "$lib/helpers/apiClient";
    import {goto} from "$app/navigation";
    import {sessionStore} from "$lib/stores/sessionStore";
    import {ProgressRadial} from "@skeletonlabs/skeleton";
    import {getTokenUrl} from "$lib/auth/authUtils";

    const finishLoading = () => {
        sessionStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: false,
            }
        });
    }

    onMount(async () => {
        const searchParams = new URL(window.location.href).searchParams;
        const code = searchParams?.get("code");
        const verifier = sessionStorage.getItem("codeVerifier");
        sessionStore.update((prevState) => {
            return {
                ...prevState,
                isLoading: true,
            }
        });
        if (code && verifier) {
            const url = getTokenUrl(code, verifier);
            const res = await authClient.getToken(url);
            if (res?.id_token) {
                sessionStorage.setItem("id_token", res.id_token);
                setTimeout(async () => {
                    const res = await apiClient.loadUser();
                    sessionStore.update(() => {
                        return {
                            isLoading: false,
                            user: res.data,
                        }
                    });
                    await goto("/");
                }, 500);
            } else {
                await goto("/");
                finishLoading();
            }
        } else {
            finishLoading();
        }
    });

</script>

<ProgressRadial
        class="mx-auto my-40"
        stroke={80}
        meter="stroke-primary-500"
        track="stroke-primary-500/30"
        width="w-12"/>