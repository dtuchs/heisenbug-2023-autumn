import { writable } from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";

export const paintingsStore = writable<{
    paintings: PaintingType[],
    noMoreData: boolean,
    isLoading: boolean,
}>({paintings: [], noMoreData: false, isLoading: false});