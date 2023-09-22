import type { PageLoad } from "./$types";
import { artists } from '../../mock/artists';

export const load: PageLoad = ({}) => {
		return {
			artists,
		};
};