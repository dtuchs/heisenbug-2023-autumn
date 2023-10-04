<script lang="ts">
	import { SearchIcon } from "$lib/types/Icon";
    import {sessionStore} from "$lib/stores/sessionStore";

    
    let search = "";

    export let searchPlaceholder: string;
    export let title: string;
    export let addButtonName: string;
    export let onAddButtonClick: () => void;
    export let loadFunction: (search: string) => {};

</script>

<div class="flex items-center justify-between m-4">
    <h2 class="text-3xl m-4">{title}</h2>
    {#if $sessionStore.user}
        <button type="button" class="btn variant-filled-primary ml-4" on:click={onAddButtonClick}>{addButtonName}</button>
    {/if}
</div>

<div class="flex justify-center mb-4 mx-8">
    <input class="input" bind:value={search} title={searchPlaceholder} type="search" placeholder={searchPlaceholder} on:keypress={(evt) => {
        if(evt.key === "Enter") {
            loadFunction(search);
        }
    }}/>
    <button type="button" class="btn-icon variant-soft-surface ml-4" on:click={() => loadFunction(search)}>
        <img src={SearchIcon} alt="Иконка поиска"/>
    </button>
</div>