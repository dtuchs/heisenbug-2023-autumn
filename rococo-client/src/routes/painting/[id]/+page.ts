import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";

export const load: PageLoad = async ({params}) => {
    const painting = await apiClient.loadPainting(params.id);
    return {
        painting,
        artist: painting.artist,
    };
};