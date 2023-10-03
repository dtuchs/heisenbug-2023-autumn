import {writable} from "svelte/store";

export const paintingFormErrorStore = writable<{
    title: string,
    description: string,
    content: string,
    authorId: string,
    museumId: string,
}>({title: "", description: "", content: "", authorId: "", museumId: ""});