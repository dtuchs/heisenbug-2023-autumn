<script lang="ts">
    import type {PaintingType} from "$lib/types/Painting";

    export let paintings: PaintingType[];
</script>


<ul class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 p-4">
    {#each paintings as painting(painting.id)}
        <li>
            <a href={`/painting/${painting?.id}`}>
                <img class="max-w-full rounded-lg object-cover w-full h-96" src={painting.content} alt={painting.title}>
                <div class="text-center">{painting?.title}</div>
            </a>
        </li>
    {/each}
</ul>
