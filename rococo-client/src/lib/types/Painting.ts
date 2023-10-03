import type {IdDto} from "$lib/types/IdDto";

export interface PaintingType extends IdDto{
    authorId: string,
    content: string,
    title: string,
    description: string,
    museumId: string,
}

export type NewPaintingType = {
    artist: {
        id: string,
    },
    content: string,
    title: string,
    description: string,
    museum?: {
        id?: string,
    },
}