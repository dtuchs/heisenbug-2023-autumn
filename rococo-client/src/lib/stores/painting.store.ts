import { writable } from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";
import type {StoreDataType} from "$lib/types/DataType";

export const paintingsStore = writable<StoreDataType<PaintingType>>({
    data: [], noMoreData: true, isLoading: false, ignoreIds: []});