import {writable} from "svelte/store";
import type {MuseumType} from "$lib/types/Museum";

export const singleMuseumStore = writable<{
    museum: MuseumType | undefined,
}>({museum: undefined});