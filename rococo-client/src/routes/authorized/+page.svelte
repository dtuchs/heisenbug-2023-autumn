<script>

    import {onMount} from "svelte";
    import {authClient} from "$lib/helpers/authClient";
    import {apiClient} from "$lib/helpers/apiClient";
    import {goto} from "$app/navigation";
    import {sessionStore} from "$lib/stores/sessionStore";
    import {ProgressRadial} from "@skeletonlabs/skeleton";

    onMount(async () => {
        const searchParams = new URL(window.location.href).searchParams;
        if (searchParams?.get("code")) {
            const code = searchParams?.get("code");
            const client = "client";

            const verifier = sessionStorage.getItem("codeVerifier");
            const initialUrl = `oauth2/token?client_id=${client}&redirect_uri=http://127.0.0.1:3000/authorized&grant_type=authorization_code`;
            const url = `${initialUrl}&code=${code}&code_verifier=${verifier}`;

            const res = await authClient.getToken(url);
            if (res?.id_token) {
                sessionStorage.setItem("id_token", res.id_token);
                setTimeout(async () => {
                    const res = await apiClient.loadUser();
                    sessionStore.update(() => {
                        return {
                            user: res,
                        }
                    });
                    await goto("/");
                }, 500);
            } else {
                await goto("/");
            }
        }
    });

</script>

<ProgressRadial
        class="mx-auto my-40"
        stroke={80}
        meter="stroke-primary-500"
        track="stroke-primary-500/30"
        width="w-12"/>