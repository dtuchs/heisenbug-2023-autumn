<script lang="ts">
	import { Avatar } from '@skeletonlabs/skeleton';
	import type { PageData } from './$types';

    export let data: PageData;
</script>


<article class="card m-6">
    <header class="card-header text-center font-bold text-2xl">{data?.artist?.name}</header>
    <section class="p-4 grid grid-cols-3 justify-items-center">
        <Avatar src={data?.artist?.photo} width="w-56" rounded="rounded-full" class="col-span-1"/>	
        <p class="col-span-2">{data?.artist?.biography}</p>		
    </section>
    <section class="p-4">
        <h3 class="text-center font-bold text-xl">Картины автора</h3>
        <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
            {#each data.paintings as painting}
                <a href={`/painting/${painting.id}`}>
                    <img class="h-auto max-w-full rounded-lg object-cover w-full h-80" src={painting.src} alt={painting.title}>
                    <div class="text-center">{painting.title}, {painting.year}</div>
                </a>
            {/each}
        </div>
    </section>
</article>
	
