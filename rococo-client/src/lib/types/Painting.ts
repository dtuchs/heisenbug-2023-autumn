export type PaintingType = {
    id: string,
    authorId: string,
    content: string,
    title: string,
    description: string,
    museumId: string,
}

export type NewPaintingType = {
    artist: {
        id: string
    },
    content: string,
    title: string,
    description: string,
    museumId?: string,
}