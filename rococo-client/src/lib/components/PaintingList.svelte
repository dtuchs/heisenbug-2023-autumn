<script lang="ts">
    import { paintingsStore } from '$lib/stores/painting.store';
</script>


<ul class="grid grid-cols-2 md:grid-cols-3 gap-4 p-4">
    {#each $paintingsStore.paintings as painting(painting.id)}
        <li>
            <a href={`/painting/${painting?.id}`}>
                <img class="max-w-full rounded-lg object-cover w-full h-80" src={painting.src} alt={painting.title}>
                <div class="text-center">{painting?.title}</div>
            </a>
        </li>
    {/each}
</ul>
