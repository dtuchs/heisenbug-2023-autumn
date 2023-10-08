<script lang="ts">
	import type { PageData } from './$types';
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import MuseumPageContent from "$lib/components/content/MuseumPageContent.svelte";

    export let data: PageData;


</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <MuseumPageContent errorTrigger={triggerError} successTrigger={triggerSuccess} data={data}/>
</ToastHandler>