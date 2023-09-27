<script lang="ts">
	import { SearchIcon } from "$lib/types/Icon";

    export let text: string;
    export let description: string;

</script>


<section class="grid grid-cols-1 p-4">
    <div class="m-20 py-20 border rounded-container-token border-surface-500 text-center">
        <img src={SearchIcon} alt="Иконка поиска" class="w-50 h-50 mx-auto" width="50" height="50"/>
        <p class="mx-4 my-10 text-xl">{text}</p>
        <p class="mx-4 my-10 text-l">{description}</p>
    </div>
</section>