<script lang="ts">
    import {onMount} from "svelte";
    import {
        initLocalStorageAndRedirectToAuth
    } from "$lib/api/authUtils";
    import Loader from "$lib/components/Loader.svelte";

    onMount(async () => {
        await initLocalStorageAndRedirectToAuth();
    });
</script>

<Loader/>