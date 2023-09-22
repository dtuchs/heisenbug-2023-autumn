import type { PageLoad } from "./$types";
import { paintings } from '../../mock/paintings';

export const load: PageLoad = ({}) => {
    return {
        paintings,
    };
};