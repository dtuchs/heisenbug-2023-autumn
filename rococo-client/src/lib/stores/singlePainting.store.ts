import {writable} from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";

export const singlePaintingStore = writable<{
    painting: PaintingType | undefined,
}>({painting: undefined});