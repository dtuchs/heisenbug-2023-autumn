export type ArtistType = {
    id: string,
    name: string,
    photo: string,
    biography: string,
}

export type NewArtistType = {
    name: string,
    photo: string | ArrayBuffer | null,
    biography: string,
}