import { apiClient } from "$lib/helpers/apiClient";
import type { PageLoad } from "./$types";

export const load: PageLoad = async () => {

	const artists = await apiClient.loadArtists();
	
	return {
		artists: artists.content,
	};
};