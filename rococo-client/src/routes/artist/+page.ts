import { apiClient } from "$lib/helpers/apiClient";
import type { PageLoad } from "./$types";

export const load: PageLoad = async () => {

	const data = await apiClient.loadArtists({page: 0, size: 18});
	
	return {
		...data
	};
};