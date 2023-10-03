import type { PageLoad } from "./$types";
import { apiClient } from "$lib/helpers/apiClient";

export const load: PageLoad = async ({params}) => {

	const artist = await apiClient.loadArtist(params.id);
	const paintings = await apiClient.loadPaintingsByAuthorId({authorId: params.id});
	
	return {
		artist,
		paintings: paintings.content,
		currentPage: paintings.currentPage,
		totalPages: paintings.totalPages,
	};
};