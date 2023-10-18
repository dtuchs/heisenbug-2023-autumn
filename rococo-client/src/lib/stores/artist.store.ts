import type { ArtistType } from "$lib/types/Artist";
import { writable } from "svelte/store";
import type {StoreDataType} from "$lib/types/DataType";

export const artistsStore = writable<StoreDataType<ArtistType>>(
    {data: [], noMoreData: true, isLoading: false, ignoreIds: []});

