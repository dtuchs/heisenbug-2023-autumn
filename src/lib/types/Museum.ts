export type MuseumType = {
    id: number,
    photo: string,
    name: string,
    description: string,
    country: string,
    city: string,
    location: {
        lat: number,
        lng: number,
    }
}