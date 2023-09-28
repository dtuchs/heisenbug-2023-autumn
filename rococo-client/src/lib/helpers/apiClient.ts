import type { NewArtistType } from "$lib/types/Artist";
import type { NewPaintingType } from "$lib/types/Painting";

const BASE_URL = 'http://127.0.0.1:8080/api';

export const apiClient = {
    loadArtists: async ({ page = 0, size = 18, search}
        : {
            page?: number,
            size?: number,
            search?: string
        }) => {
        return loadItems({path: "artist", search, page, size});
    },
    loadArtist: async(id: string) => {
        return loadItem("artist", id);
    },
    addArtist: async(newArtist: NewArtistType) => {
        const res = await fetch(`${BASE_URL}/artist`, {
            method: "POST", 
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newArtist),
        });

        return await res.json();
    },
    loadPaintings: async(search?: string) => {
        return loadItems({path: "painting", search});
    },
    loadPainting: async(id: string) => {
       return loadItem("painting", id);
    },
    addPainting: async(newPainting: NewPaintingType) => {
        const res = await fetch(`${BASE_URL}/painting`, {
            method: "POST", 
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newPainting),
        });
        return await res.json();
    },
    loadMuseums: async({ page = 0, size = 4, search}
        : {
            page?: number,
            size?: number,
            search?: string
        }) => {
        return loadItems({path: "museum", search, page, size});
    },
    loadMuseum: async(id: string) => {
        return loadItem("museum", id);
    },
}

const loadItem = async (path: string, id: string) => {
    const response = await fetch(`${BASE_URL}/${path}/${id}`);
    if (!response.ok) {
        throw new Error(`Failed loading ${path} with id ${id}`);
    }
    return response.json();
};

const loadItems = async({ path, page = 0, size = 5, search}
    : {
        path: string, 
        page?: number,
        size?: number,
        search?: string
    }) => {
    const query = search ? `?name=${search}` : `?size=${size}&page=${page}`;
    const response = await fetch(`${BASE_URL}/${path}${query}`);
    if (!response.ok) {
        throw new Error("Failed loading data");
    }
    return response.json();
}

