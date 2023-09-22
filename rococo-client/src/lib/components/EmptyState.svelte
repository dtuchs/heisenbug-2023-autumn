<script lang="ts">

    export let buttonName = "Добавить";
    export let text: string;
    export let onButtonClick: () => void;

</script>


<section class="grid grid-cols-1 p-4">
    <div class="m-20 py-20 border rounded-container-token border-surface-500 text-center">
        <p class="mx-4 my-10 text-xl">{text}</p>
        <button type="button" class="btn variant-filled-primary ml-4" on:click={onButtonClick}>{buttonName}</button>	
    </div>
</section>