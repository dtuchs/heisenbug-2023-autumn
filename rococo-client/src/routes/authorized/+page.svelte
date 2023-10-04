<script>

    import {onMount} from "svelte";
    import {authClient} from "$lib/helpers/authClient";

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
            }
        }
    });

</script>

<h1>Authorized</h1>