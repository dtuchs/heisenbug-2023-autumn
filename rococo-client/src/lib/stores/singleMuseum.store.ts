import {writable} from "svelte/store";
import type {MuseumType} from "$lib/types/Museum";
import type {SingleStoreDataType} from "$lib/types/DataType";

export const singleMuseumStore = writable<SingleStoreDataType<MuseumType>>
({data: undefined});