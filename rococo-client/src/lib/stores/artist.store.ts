import type { ArtistType } from "$lib/types/Artist";
import { writable } from "svelte/store";

export const artistsStore = writable<{
    artists: ArtistType[],
    noMoreData: boolean,
    isLoading: boolean,
    ignoreIds: string [],
}>({artists: [], noMoreData: false, isLoading: false, ignoreIds: []});

