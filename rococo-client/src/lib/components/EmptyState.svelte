<script lang="ts">

    import {sessionStore} from "$lib/stores/sessionStore";

    export let buttonName = "Добавить";
    export let text: string;
    export let onButtonClick: () => void;
    export let bordered = true;
    export let fullPage = true;

</script>


<section class={`grid grid-cols-1 ${fullPage ? "p-4" : ""}`}>
    <div 
        class={`${fullPage ? "m-20 py-20" : "mb-20 mt-4"} rounded-container-token border-surface-500 text-center`}
        class:border={bordered}>
        <p class="mx-4 my-10 text-xl">{text}</p>
        {#if $sessionStore.user}
            <button type="button" class="btn variant-filled-primary ml-4" on:click={onButtonClick}>{buttonName}</button>
        {/if}
    </div>
</section>