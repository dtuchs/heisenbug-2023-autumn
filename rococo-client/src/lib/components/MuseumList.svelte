<script lang="ts">
    import { museumsStore } from '$lib/stores/museum.store';
</script>

<ul class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 p-4">
    {#each $museumsStore.data as museum(museum.id)}
        <li>
            <a href={`/museum/${museum.id}`}>
                <img class="max-w-full rounded-lg object-cover w-full h-96" src={museum.photo} alt={museum.title}>
                <div class="text-center">{museum.title}</div>
                <div class="text-center">{museum.geo.city}, {museum.geo.country.name}</div>
            </a>
        </li>
    {/each}
</ul>
