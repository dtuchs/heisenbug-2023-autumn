import {writable} from "svelte/store";

export const userFormErrorStore = writable<{
    firstname: string,
    lastname: string,
    avatar: string,
}>({firstname: "", lastname: "", avatar: ""});