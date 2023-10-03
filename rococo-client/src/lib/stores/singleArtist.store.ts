import {writable} from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";
import type {ArtistType} from "$lib/types/Artist";

export const singleArtistStore = writable<{
    artist: ArtistType | undefined,
    paintings: PaintingType[],
    noMoreData: boolean,
    isLoading: boolean,
    ignoreIds: string [],
}>({artist: undefined,paintings: [], noMoreData: false, isLoading: false, ignoreIds: []});