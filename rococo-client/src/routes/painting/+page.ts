import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";


export const load: PageLoad = async ({}) => {

    const data = await apiClient.loadPaintings({page: 0, size: 9});

    return {
        ...data
    };
};