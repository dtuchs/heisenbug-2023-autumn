<script lang="ts">
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import MuseumPageContent from "$lib/components/content/MuseumPageContent.svelte";

</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <MuseumPageContent errorTrigger={triggerError} successTrigger={triggerSuccess}/>
</ToastHandler>