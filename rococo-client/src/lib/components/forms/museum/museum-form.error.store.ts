import {writable} from "svelte/store";

export const museumFormErrorStore = writable<{
    title: string,
    description: string,
    photo: string,
    city: string,
    countryId: string,
}>({title: "", description: "", photo: "", city: "", countryId: ""});