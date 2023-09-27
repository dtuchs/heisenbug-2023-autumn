<script lang="ts">

    export let label: string;
    export let name: string;
    export let value: string;
    export let error: string;
    export let placeholder = label;
    export let required = false;

</script>

<label class="label">
    <span>{label}</span>
    <textarea 
        {name}
        class="textarea" 
        class:input-error={error.length}
        rows="4" 
        bind:value={value} 
        on:input={() => error = ""}
        {placeholder}
        {required}
         />
    <span class="text-error-400">{error}</span>
</label>