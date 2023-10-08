<script lang="ts">
	import type { PageData } from './$types';
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import PaintingPageContent from "$lib/components/content/PaintingPageContent.svelte";

    export let data: PageData;

</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <PaintingPageContent errorTrigger={triggerError} successTrigger={triggerSuccess} data={data}/>
</ToastHandler>


