import type {IdDto} from "$lib/types/IdDto";

export interface PaintingType extends IdDto{
    artist: {
        id: string,
        name?: string,
    },
    content: string,
    title: string,
    description: string,
    museum?: {
        id?: string,
    },
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