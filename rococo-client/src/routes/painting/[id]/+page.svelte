<script lang="ts">
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import PaintingPageContent from "$lib/components/content/PaintingPageContent.svelte";

</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <PaintingPageContent errorTrigger={triggerError} successTrigger={triggerSuccess}/>
</ToastHandler>


