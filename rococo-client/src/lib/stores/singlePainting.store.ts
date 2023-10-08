import {writable} from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";
import type {SingleStoreDataType} from "$lib/types/DataType";

export const singlePaintingStore = writable<SingleStoreDataType<PaintingType>>
({data: undefined});