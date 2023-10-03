import type {ArtistType, NewArtistType} from "$lib/types/Artist";
import type { NewPaintingType } from "$lib/types/Painting";
import type {NewMuseumType} from "$lib/types/Museum";

const BASE_URL = 'http://127.0.0.1:8080/api';

export const apiClient = {
    loadArtists: async ({ page = 0, size = 18, search}
        : { page?: number, size?: number, search?: string }) => {
        return loadItems({path: "artist", search, page, size, searchName: "name"});
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
    updateArtist: async(artist: ArtistType) => {
        const res = await fetch(`${BASE_URL}/artist`, {
            method: "PATCH",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(artist),
        });

        return await res.json();
    },
    loadPaintings: async({ page = 0, size = 9, search}
                             : {
        page?: number,
        size?: number,
        search?: string
    }) => {
        return loadItems({path: "painting", search, page, size, searchName: "title"});
    },
    loadPaintingsByAuthorId: async({ authorId, page = 0, size = 9, search}
                             : {
        authorId: string,
        page?: number,
        size?: number,
        search?: string
    }) => {
        return loadItems({path: `painting/author/${authorId}`, search, page, size, searchName: "title"});
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
    addMuseum: async(newMuseum: NewMuseumType) => {
        const res = await fetch(`${BASE_URL}/museum`, {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newMuseum),
        });
        return await res.json();
    },
    loadMuseum: async(id: string) => {
        return loadItem("museum", id);
    },
    loadCountries: async({page = 0, size = 20}: {
        page?: number,
        size?: number,
    }) => {
        return loadItems({path: "country", page, size});
    },
}

const loadItem = async (path: string, id: string) => {
    const response = await fetch(`${BASE_URL}/${path}/${id}`);
    if (!response.ok) {
        throw new Error(`Failed loading ${path} with id ${id}`);
    }
    return response.json();
};

const loadItems = async({ path, page = 0, size = 5, search, searchName}
    : {
        path: string, 
        page?: number,
        size?: number,
        search?: string,
        searchName?: string,
    }) => {
    const query = search ? `?${searchName ?? "name"}=${search}` : `?size=${size}&page=${page}`;
    const response = await fetch(encodeURI(`${BASE_URL}/${path}${query}`));
    if (!response.ok) {
        throw new Error("Failed loading data");
    }
    return response.json();
}

