import type {ArtistType, NewArtistType} from "$lib/types/Artist";
import type {NewPaintingType, PaintingType} from "$lib/types/Painting";
import type {MuseumType, NewMuseumType} from "$lib/types/Museum";

const BASE_URL = 'http://127.0.0.1:8080/api';

export const apiClient = {
    loadArtists: async ({ page = 0, size = 18, search}
        : { page?: number, size?: number, search?: string }) => {
        const query = search ? `?name=${search}` : `?size=${size}&page=${page}`;
        return commonFetch({
            method: "GET",
            urlPart: `artist${query}`,
        });
    },
    loadArtist: async(id: string) => {
        return commonFetch({
            method: "GET",
            urlPart: `artist/${id}`,
        });
    },
    addArtist: async(newArtist: NewArtistType) => {
        return await commonFetch({
            method: "POST",
            urlPart: "artist",
            body: JSON.stringify(newArtist),
        });
    },
    updateArtist: async(artist: ArtistType) => {
        return await commonFetch({
            method: "PATCH",
            urlPart: "artist",
            body: JSON.stringify(artist),
        });
    },
    loadPaintings: async({ page = 0, size = 9, search}: {
        page?: number,
        size?: number,
        search?: string
    }) => {
        const query = search ? `?title=${search}` : `?size=${size}&page=${page}`;
        return commonFetch({
            method: "GET",
            urlPart: `painting${query}`,
        });
    },
    loadPaintingsByAuthorId: async({ authorId, page = 0, size = 9, search}: {
        authorId: string,
        page?: number,
        size?: number,
        search?: string
    }) => {
        const query = search ? `?title=${search}` : `?size=${size}&page=${page}`;

        return commonFetch({
            method: "GET",
            urlPart: `painting/author/${authorId}${query}`,
        });
    },
    loadPainting: async(id: string) => {
        return commonFetch({
            method: "GET",
            urlPart: `painting/${id}`,
        });
    },
    addPainting: async(newPainting: NewPaintingType) => {
        return await commonFetch({
            method: "POST",
            urlPart: "painting",
            body: JSON.stringify(newPainting),
        });
    },
    updatePainting: async(painting: PaintingType) => {
        return await commonFetch({
            method: "PATCH",
            urlPart: "painting",
            body: JSON.stringify(painting),
        });
    },
    loadMuseums: async({ page = 0, size = 4, search}: {
            page?: number,
            size?: number,
            search?: string
        }) => {
        const query = search ? `?title=${search}` : `?size=${size}&page=${page}`;
        return commonFetch({
            method: "GET",
            urlPart: `museum${query}`,
        });
    },
    loadMuseum: async(id: string) => {
        return commonFetch({
            method: "GET",
            urlPart: `museum/${id}`,
        });
    },
    addMuseum: async(newMuseum: NewMuseumType) => {
        return await commonFetch({
            method: "POST",
            urlPart: "museum",
            body: JSON.stringify(newMuseum),
        });
    },
    updateMuseum: async(museum: MuseumType) => {
        return await commonFetch({
            method: "PATCH",
            urlPart: "museum",
            body: JSON.stringify(museum),
        });
    },
    loadCountries: async({page = 0, size = 20}: {
        page?: number,
        size?: number,
    }) => {
        const query = `?size=${size}&page=${page}`;
        return commonFetch({
            method: "GET",
            urlPart: `country${query}`,
        });
    },
    loadSession: async() => {
        return commonFetch({
            method: "GET",
            urlPart: "session",
        });
    },
    loadUser: async() => {
        return commonFetch({
            method: "GET",
            urlPart: "user",
        });
    },
}
const commonFetch = async (
    { urlPart, method, body }: {
    urlPart: string,
    method: string,
    body?: BodyInit | null,
    }) => {
    const response = await fetch(`${BASE_URL}/${urlPart}`, {
        method,
        headers: {
            "Accept": "application/json",
            "Authorization": `Bearer ${sessionStorage.getItem("id_token")}`,
            "Content-Type": "application/json",
        },
        body,
    });
    if (!response.ok) {
        throw new Error("Failed loading data");
    }
    return response.json();
};

