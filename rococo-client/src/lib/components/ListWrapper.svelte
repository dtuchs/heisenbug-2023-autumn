<script lang="ts">
    import EmptySearch from "$lib/components/EmptySearch.svelte";
    import EmptyState from "$lib/components/EmptyState.svelte";
    import {ProgressRadial} from "@skeletonlabs/skeleton";
    import viewport from "$lib/hooks/useViewport";

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

{#if data?.length === 0}
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
{:else}
    <div class="w-100">
        <slot/>
        {#if !noMoreData}
            <div use:viewport on:viewportenter={() => loadMore()}>
                {#if isLoading}
                    <ProgressRadial
                            class="mx-auto my-20"
                            stroke={80}
                            meter="stroke-primary-500"
                            track="stroke-primary-500/30"
                            width="w-12"/>
                {/if}
            </div>
        {/if}
    </div>
{/if}