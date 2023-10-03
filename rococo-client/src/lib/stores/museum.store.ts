import { writable } from "svelte/store";
import type {MuseumType} from "$lib/types/Museum";

export const museumsStore = writable<{
    museums: MuseumType[],
    noMoreData: boolean,
    isLoading: boolean,
    ignoreIds: string [],
}>({museums: [], noMoreData: true, isLoading: false, ignoreIds: []});