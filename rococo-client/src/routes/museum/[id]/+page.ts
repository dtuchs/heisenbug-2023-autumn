import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";


export const load: PageLoad = async ({params}) => {
    const museum = await apiClient.loadMuseum(params.id);
    return {
        museum
    };
};