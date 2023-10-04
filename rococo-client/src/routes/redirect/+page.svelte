<script>
    import {ProgressRadial} from "@skeletonlabs/skeleton";
    import {onMount} from "svelte";
    import {generateCodeChallenge, generateCodeVerifier} from "$lib/auth/authUtils";
    import {goto} from "$app/navigation";
    onMount(async () => {
        const codeVerifier = generateCodeVerifier();
        sessionStorage.setItem('codeVerifier', codeVerifier);
        const codeChallenge = generateCodeChallenge();
        sessionStorage.setItem('codeChallenge', codeChallenge);

        const link = `http://127.0.0.1:9000/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=${codeChallenge}&code_challenge_method=S256`;
        await goto(link);
    });
</script>


<ProgressRadial
        class="mx-auto my-40"
        stroke={80}
        meter="stroke-primary-500"
        track="stroke-primary-500/30"
        width="w-12"/>