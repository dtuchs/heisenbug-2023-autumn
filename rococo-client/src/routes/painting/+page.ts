import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";


export const load: PageLoad = async ({}) => {

    const paintings = await apiClient.loadPaintings({page: 0, size: 9});

    return {
        paintings: paintings.content,
        currentPage: paintings.currentPage,
        totalPages: paintings.totalPages,
    };
};