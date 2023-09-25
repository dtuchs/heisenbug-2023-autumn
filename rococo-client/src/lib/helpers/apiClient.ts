import type { ArtistType, NewArtistType } from "$lib/types/Artist";

const BASE_URL = 'http://localhost:8080/api';

export const apiClient = {
    loadArtists: async (search?: string) => {
        return loadItems("artist", search);
    },
    loadArtist: async(id: string) => {
        return loadItem("artist", id);
    },
    addArtist: async(formData: FormData) => {
        await fetch(`${BASE_URL}/artist`, {
            method: "POST", 
            body: formData,
        });
    },
    loadPaintings: async(search?: string) => {
        return loadItems("painting", search);
    },
    loadPainting: async(id: string) => {
       return loadItem("painting", id);
    },
    loadMuseums: async(search?: string) => {
        return loadItems("museum", search);
    },
    loadMuseum: async(id: string) => {
        return loadItem("museum", id);
    },
}

const loadItem = async (path: string, id: string) => {
    const response = await fetch(`${BASE_URL}/${path}/${id}`);
    if (!response.ok) {
        throw new Error(`Failed loading ${path} with id ${id}`);
    };
    return response.json();
};

const loadItems = async(path: string, search?: string) => {
    const query = search ? `?seacrh=${search}` : "";
    const response = await fetch(`${BASE_URL}/${path}${query}`);
    if (!response.ok) {
        throw new Error("Failed loading data");
    };
    return response.json();
}

