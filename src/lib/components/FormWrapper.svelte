<script lang="ts">
    export let modalTitle: string;
    export let modalBody: string;
</script>

<div class="card p-4 w-modal shadow-xl space-y-4">
    <header class="text-2xl font-bold">{modalTitle}</header>
    <article>{modalBody}</article>
    <slot/>
</div>