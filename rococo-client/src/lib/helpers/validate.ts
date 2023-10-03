import {Errors} from "$lib/types/Errors";

export const validateImage = (src: File) => {
    return src.size > 120_000_000 ? Errors.IMAGE_CONSTRAINT_TOO_BIG : "";
}