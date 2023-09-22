<script lang="ts">
	import type { PageData } from './$types';

    export let data: PageData;
</script>


<article class="card m-8 p-4">
    <header class="card-header text-center font-bold">{data?.painting?.title}, {data?.painting?.year}</header>
    <div class="text-center">{data?.author?.name}</div>
    <img src={data?.painting?.src} alt={data?.painting?.title} class="max-w-xl my-4 mx-auto"/>
    <div>{data?.painting?.description}</div>
</article>