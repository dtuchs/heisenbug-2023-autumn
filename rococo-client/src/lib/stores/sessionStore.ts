import {writable} from "svelte/store";
import type {UserType} from "$lib/types/User";

export const sessionStore = writable<{
    user: UserType | undefined,
}>({user: undefined});