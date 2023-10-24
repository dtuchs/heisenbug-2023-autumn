<script lang="ts">

    import ToastHandler from "$lib/components/ToastHandler.svelte";
    import AuthorizedContent from "$lib/components/content/AuthorizedContent.svelte";

</script>

<ToastHandler let:triggerError let:triggerSuccess>
    <AuthorizedContent {triggerError}/>
</ToastHandler>