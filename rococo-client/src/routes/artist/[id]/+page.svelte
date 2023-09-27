<script lang="ts">
	import { Avatar, getModalStore} from '@skeletonlabs/skeleton';
	import type { PageData } from './$types';
	import EmptyState from '$lib/components/EmptyState.svelte';
    import NewPaintingForm from '$lib/components/forms/NewPaintingForm.svelte';
    import { prepareModal } from '$lib/helpers/prepareModal';

    const modalStore = getModalStore();

    export let data: PageData;

    const clickAddButton = () => {
		const modal = prepareModal(
			NewPaintingForm,
			"Новая картина", 
            "Заполните форму, чтобы добавить новую картину");
        modalStore.trigger(modal);
	};
    
</script>


<article class="card m-6">
    <header class="card-header text-center font-bold text-2xl">{data?.artist?.name}</header>
    <section class="p-4 grid grid-cols-3 justify-items-center">
        <Avatar src={data?.artist?.photo} width="w-56" rounded="rounded-full" class="col-span-1"/>	
        <p class="col-span-2">{data?.artist?.biography}</p>		
    </section>
    <section class="p-4">
        {#if data?.paintings?.length === 0}
            <EmptyState
                text="Пока что список картин этого художника пуст."
                buttonName="Добавить картину"
                onButtonClick={clickAddButton}
                bordered={false}
                fullPage={false}
                />
        {:else}
            <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
                {#each data.paintings as painting}
                    <a href={`/painting/${painting.id}`}>
                        <img class="h-auto max-w-full rounded-lg object-cover w-full h-80" src={painting.src} alt={painting.title}>
                        <div class="text-center">{painting.title}</div>
                    </a>
                {/each}
            </div>
        {/if}     
    </section>
</article>
	
