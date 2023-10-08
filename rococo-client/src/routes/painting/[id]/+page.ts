import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";

export const load: PageLoad = async ({params}) => {
    const data = await apiClient.loadPainting(params.id);
    return {
        ...data,
    };
};