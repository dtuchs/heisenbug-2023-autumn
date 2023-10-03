export type PaintingType = {
    id: string,
    authorId: string,
    src: string,
    title: string,
    description: string,
    museumId: string,
}

export type NewPaintingType = {
    author: {
        id: string
    },
    src: string,
    title: string,
    description: string,
    museumId?: string,
}