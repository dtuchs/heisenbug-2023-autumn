<script lang="ts">
    import EmptySearch from "$lib/components/EmptySearch.svelte";
    import EmptyState from "$lib/components/EmptyState.svelte";
    import viewport from "$lib/hooks/useViewport";
    import Loader from "$lib/components/Loader.svelte";

    export let data: any[];
    export let isSearchNotEmpty: boolean;
    export let emptySearchText: string;
    export let emptySearchDescription: string;
    export let emptyStateTitle: string;
    export let emptyStateButtonName: string;
    export let noMoreData: boolean;
    export let isLoading: boolean;
    export let clickAddButton: () => void;
    export let loadMore: () => void;

    export let bordered = true;
    export let fullPage = true;

</script>

{#if !data?.length}
    {#if isLoading}
        <Loader/>
    {:else}
        {#if isSearchNotEmpty}
            <EmptySearch
                    text={emptySearchText}
                    description={emptySearchDescription}
            />
        {:else}
            <EmptyState
                    text={emptyStateTitle}
                    buttonName={emptyStateButtonName}
                    onButtonClick={clickAddButton}
                    {bordered}
                    {fullPage}
            />
        {/if}
    {/if}
{:else}
    <div class="w-100">
        <slot/>
    </div>
{/if}
{#if !noMoreData}
    <div use:viewport on:viewportenter={() => loadMore()}>
        {#if isLoading}
            <Loader/>
        {/if}
    </div>
{/if}