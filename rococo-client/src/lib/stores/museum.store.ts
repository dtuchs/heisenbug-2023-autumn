import { writable } from "svelte/store";
import type {MuseumType} from "$lib/types/Museum";
import type {StoreDataType} from "$lib/types/DataType";

export const museumsStore = writable<StoreDataType<MuseumType>>(
    {data: [], noMoreData: true, isLoading: false, ignoreIds: []});