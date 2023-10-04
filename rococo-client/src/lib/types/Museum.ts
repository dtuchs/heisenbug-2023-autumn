import type {IdDto} from "$lib/types/IdDto";

export interface MuseumType extends IdDto {
    photo: string,
    title: string,
    description: string,
    geo: {
        country: {
            id: string,
            name?: string,
        },
        city: string,
    }
}

export type NewMuseumType = {
    title: string,
    description: string,
    photo: string,
    geo: {
        country: {
            id: string,
        },
        city: string,
    }
}