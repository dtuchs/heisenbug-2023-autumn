<script lang="ts">
    import { museumsStore } from '$lib/stores/museum.store';
</script>

<ul class="grid grid-cols-1 md:grid-cols-2 gap-4 p-4">
    {#each $museumsStore.museums as museum(museum.id)}
        <li>
            <a href={`/museum/${museum.id}`}>
                <img class="max-w-full rounded-lg object-cover w-full h-96" src={museum.photo} alt={museum.name}>
                <div class="text-center">{museum.name}</div>
                <div class="text-center">{museum.city}, {museum.country}</div>
            </a>
        </li>
    {/each}
</ul>
