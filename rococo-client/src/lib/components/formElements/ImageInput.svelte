<script lang="ts">

    export let label: string;
    export let name: string;
    export let files: FileList;
    export let error: string;
    export let placeholder = label;
    export let required = false;
    const authorizedExtensions = ['.jpg', '.jpeg', '.png', '.webp'];

</script>

<label class="label">
    <span>{label}</span>
    <input 
        {name}
        class="input"
        class:input-error={error.length}
        type="file" 
        bind:files={files} 
        on:input={() => error = ""}
        accept={authorizedExtensions.join(',')}
        {placeholder}
        {required}
        />
    <span class="text-error-400">{error}</span>
</label>