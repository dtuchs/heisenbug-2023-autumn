<script lang="ts">
	import { Avatar } from "@skeletonlabs/skeleton";
    import { artistsStore } from '$lib/stores/artist.store';

</script>

<ul class="grid grid-cols-2 md:grid-cols-6 gap-4 p-8">
    {#each $artistsStore.artists as artist(artist.id)}
        <li>
            <a href={`/artist/${artist.id}`} class="flex flex-col justify-center items-center">
                <Avatar src={artist.photo} width="w-48" rounded="rounded-full" />
                <span class="flex-auto py-4">{artist.name}</span>
            </a>
        </li>
    {/each}
</ul>
