<script lang="ts">
    import {ProgressRadial} from "@skeletonlabs/skeleton";
    import {onMount} from "svelte";
    import {generateCodeChallenge, generateCodeVerifier, getAuthLink} from "$lib/auth/authUtils";
    import {goto} from "$app/navigation";
    onMount(async () => {
        const codeVerifier = generateCodeVerifier();
        localStorage.setItem('codeVerifier', codeVerifier);
        const codeChallenge = generateCodeChallenge();
        localStorage.setItem('codeChallenge', codeChallenge);

        const link = getAuthLink(codeChallenge);
        await goto(link);
    });
</script>


<ProgressRadial
        class="mx-auto my-40"
        stroke={80}
        meter="stroke-primary-500"
        track="stroke-primary-500/30"
        width="w-12"/>