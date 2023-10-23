import {writable} from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";
import type {ArtistType} from "$lib/types/Artist";
import type {SingleStoreDataType} from "$lib/types/DataType";

export const singleArtistStore = writable<SingleStoreDataType<ArtistType> & {
    paintings: PaintingType[],
    noMoreData: boolean,
    isLoading: boolean,
    ignoreIds: string [],
}>({data: undefined, paintings: [], noMoreData: true, isLoading: false, ignoreIds: []});