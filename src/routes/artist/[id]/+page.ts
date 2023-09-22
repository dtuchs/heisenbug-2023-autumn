import type { PageLoad } from "./$types";
import { artists } from '../../../mock/artists';
import { paintings } from '../../../mock/paintings';

export const load: PageLoad = ({params}) => {
		return {
			artist: artists.find((artist) => Number(params.id) === artist.id),
            paintings: paintings.filter(painting => Number(params.id) === painting.authorId),
		};

};