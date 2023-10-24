import type {ArtistType, NewArtistType} from "$lib/types/Artist";
import type {NewPaintingType, PaintingType} from "$lib/types/Painting";
import type {MuseumType, NewMuseumType} from "$lib/types/Museum";
import type {UserType} from "$lib/types/User";
import {clearSession} from "$lib/api/authUtils";

const BASE_URL = `${import.meta.env.VITE_API_URL}/api`;

export const apiClient = {
    loadArtists: async ({ page = 0, size = 18, search}
        : { page?: number, size?: number, search?: string, }) => {
        const query = search ? `?name=${search}` : `?size=${size}&page=${page}`;
        return await commonFetch({
            method: "GET",
            urlPart: `artist${query}`,
            authenticated: false,
        });
    },
    loadArtist: async(id: string) => {
        return commonFetch({
            method: "GET",
            urlPart: `artist/${id}`,
            authenticated: false,
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
            authenticated: false,
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
            authenticated: false,
        });
    },
    loadPainting: async(id: string) => {
        return commonFetch({
            method: "GET",
            urlPart: `painting/${id}`,
            authenticated: false,
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
            authenticated: false,
        });
    },
    loadMuseum: async(id: string) => {
        return commonFetch({
            method: "GET",
            urlPart: `museum/${id}`,
            authenticated: false,
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
    updateUser: async(user: UserType) => {
        return await commonFetch({
            method: "PATCH",
            urlPart: "user",
            body: JSON.stringify(user),
        });
    }
}
const commonFetch = async (
    { urlPart, method, body, authenticated = true }: {
    urlPart: string,
    method: string,
    body?: BodyInit | null,
    authenticated?: boolean
    }) => {
    const headers= {
        "Accept": "application/json",
        "Content-Type": "application/json",
    };
    if(authenticated) {
        const token= localStorage.getItem("id_token");
        if(token) {
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            headers["Authorization"] = `Bearer ${token}`;
        }
    }
    try {
        const response = await fetch(`${BASE_URL}/${urlPart}`, {
            method,
            credentials: "include",
            headers,
            body,
        });
        if (response.status === 401) {
            clearSession();
        }
        const data = await response.json();
        if (!response.ok) {
            const errorText = data?.errors.join(". ");
            return {
                data: undefined,
                error:`${errorText ?? "Что-то пошло не так"}`
            }
        }
        return {
            data,
            error: undefined,
        }
    } catch (error: any) {
        return {
            data: undefined,
            error: error?.message,
        }
    }
};


