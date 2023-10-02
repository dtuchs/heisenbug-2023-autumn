import { apiClient } from "$lib/helpers/apiClient";
import type { PageLoad } from "./$types";

export const load: PageLoad = async () => {

	const artists = await apiClient.loadArtists({page: 0, size: 18});
	
	return {
		artists: artists.content,
		currentPage: artists.currentPage,
		totalPages: artists.totalPages,
	};
};