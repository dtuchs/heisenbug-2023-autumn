import {writable} from "svelte/store";

export const artistsFormErrorStore = writable<{
    name: string,
    biography: string,
    photo: string,
}>({name: "", biography: "", photo: ""});