<script lang="ts">

    import {onMount} from "svelte";
    import {authClient} from "$lib/api/authClient";
    import {apiClient} from "$lib/api/apiClient";
    import {goto} from "$app/navigation";
    import {sessionStore} from "$lib/stores/sessionStore";
    import {getTokenFromUrlEncodedParams} from "$lib/api/authUtils";
    import Loader from "$lib/components/Loader.svelte";

    export let triggerError: (message: string) => void;

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
            const data = getTokenFromUrlEncodedParams(code, verifier);
            const res = await authClient.getToken("oauth2/token", data);
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
                }, 500);
            } else {
                triggerError("Не удалось получить токен")
                finishLoading();
            }
        } else {
            triggerError(`Не удалось войти в кабинет: code=${code}, codeVerifier=${verifier}`);
            finishLoading();
        }
        await goto("/");
    });

</script>

<Loader/>