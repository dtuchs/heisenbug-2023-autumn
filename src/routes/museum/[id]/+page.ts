import type { PageLoad } from "./$types";
import { museums } from "../../../mock/museums";

export const load: PageLoad = ({params}) => {
    const museum = museums.find(museum => Number(params.id) === museum.id);
    return {
        museum
    };
};