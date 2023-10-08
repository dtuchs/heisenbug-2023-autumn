import type { PageLoad } from "./$types";
import { apiClient } from "$lib/helpers/apiClient";

export const load: PageLoad = async ({params}) => {

	const artistData = await apiClient.loadArtist(params.id);
	const paintingsData = await apiClient.loadPaintingsByAuthorId({authorId: params.id});
	
	return {
		artist: artistData,
		paintings: paintingsData,
	};
};