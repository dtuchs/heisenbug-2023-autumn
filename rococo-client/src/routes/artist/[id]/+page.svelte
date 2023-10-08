<script lang="ts">
	import type { PageData } from './$types';
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import ArtistPageContent from "$lib/components/content/ArtistPageContent.svelte";

    export let data: PageData;
    
</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <ArtistPageContent errorTrigger={triggerError} successTrigger={triggerSuccess} data={data}/>
</ToastHandler>
