import type { PageLoad } from "./$types";
import { paintings } from '../../../mock/paintings';
import { artists } from "../../../mock/artists";

export const load: PageLoad = ({params}) => {
    const painting = paintings.find(painting => Number(params.id) === painting.id);
    return {
        painting,
        author: artists.find(artist => artist.id === painting?.authorId),
    };
};