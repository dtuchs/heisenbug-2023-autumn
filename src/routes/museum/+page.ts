import { museums } from "../../mock/museums";
import type { PageLoad } from "./$types";

export const load: PageLoad = ({}) => {
    return {
        museums,
    };
};