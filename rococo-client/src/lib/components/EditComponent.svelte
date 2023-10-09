<script lang="ts">
import {sessionStore} from "$lib/stores/sessionStore.js";
import {prepareModal} from "$lib/helpers/prepareModal";
import {getModalStore} from "@skeletonlabs/skeleton";
import type {ComponentType} from "svelte";
import type {Writable} from "svelte/store";
import type {SingleStoreDataType} from "$lib/types/DataType.js";

type Type = $$Generic<{ id: string }>;

const modalStore = getModalStore();
export let errorTrigger: (message: string) => void;
export let successTrigger: (message: string) => void;
export let formComponent: ComponentType;
export let store: Writable<SingleStoreDataType<Type>>;
export let successMessage: string;
export let formData: Record<string, any>;

export let dataKey: string;

export let dataTestId = "edit";

const clickEditButton = () => {
    const modal = prepareModal({
        ref: formComponent,
        valueAttr: {
            ...formData,
        },
        callback: dataEditCallback,
    });
    modalStore.trigger(modal);
};

const dataEditCallback = async (result: {
    data?: Type,
    error?: string,
}) => {
    if(result.error) {
        errorTrigger(result.error);
        return;
    }
    const data = result.data;
    if(data) {
        store.update((prevState) => {
            return {
                ...prevState,
                data: data,
            }
        });
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        successTrigger(`${successMessage}: ${data[dataKey]}`);
    }
};

</script>

{#if $sessionStore.user}
    <button data-testid={dataTestId} class="btn variant-ghost m-3 mx-auto block w-full" type="button" on:click={clickEditButton}>Редактировать</button>
{/if}