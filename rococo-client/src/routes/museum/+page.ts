import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";

export const load: PageLoad = async ({}) => {

    const artists = await apiClient.loadMuseums({page: 0, size: 18});

    return {
        museums: artists.content,
        currentPage: artists.currentPage,
        totalPages: artists.totalPages,
    };
};