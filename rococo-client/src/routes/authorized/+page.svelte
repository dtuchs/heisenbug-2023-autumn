<script lang="ts">

    import {onMount} from "svelte";
    import {authClient} from "$lib/api/authClient";
    import {apiClient} from "$lib/api/apiClient";
    import {goto} from "$app/navigation";
    import {sessionStore} from "$lib/stores/sessionStore";
    import {getTokenUrl} from "$lib/api/authUtils";
    import Loader from "$lib/components/Loader.svelte";

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
        const verifier = localStorage.getItem("codeVerifier");
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
                localStorage.setItem("id_token", res.id_token);
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

<Loader/>