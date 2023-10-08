import type { PageLoad } from "./$types";
import {apiClient} from "$lib/helpers/apiClient";


export const load: PageLoad = async ({params}) => {
    const data = await apiClient.loadMuseum(params.id);
    return {
        ...data,
    };
};