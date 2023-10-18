<script lang="ts">
    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import ArtistPageContent from "$lib/components/content/ArtistPageContent.svelte";

</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <ArtistPageContent errorTrigger={triggerError} successTrigger={triggerSuccess}/>
</ToastHandler>
