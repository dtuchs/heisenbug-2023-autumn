import type { PageLoad } from "./$types";
import { paintings } from '../../../mock/paintings';
import { apiClient } from "$lib/helpers/apiClient";

export const load: PageLoad = async ({params}) => {

	const artist = await apiClient.loadArtist(params.id);
	
	return {
		artist,
		paintings: paintings.filter(painting => Number(params.id) === painting.authorId),
	};
};