import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";

export const load: PageLoad = async ({}) => {

    const data = await apiClient.loadMuseums({page: 0, size: 4});

    return {
       ...data
    };
};