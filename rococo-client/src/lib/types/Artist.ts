import type {IdDto} from "$lib/types/IdDto";

export interface ArtistType extends IdDto {
    name: string,
    photo: string,
    biography: string,
}

export type NewArtistType = {
    name: string,
    photo: string | ArrayBuffer | null,
    biography: string,
}