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
    <input 
        {name}
        class="input"
        autocomplete="off"
        class:input-error={error.length}
        type="text" 
        bind:value={value} 
        on:input={() => error = ""}
        {placeholder}
        {required}
        />
    <span class="text-error-400">{error}</span>
</label>