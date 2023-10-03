import {Errors} from "$lib/types/Errors";
import {artistsFormErrorStore} from "$lib/components/forms/artist/artist-form.error.store";

export const validateForm = (
    title: string,
    description: string,
    authorId: string) => {

    artistsFormErrorStore.update((prevState) => {
        return {
            ...prevState,
            title: title?.length < 3
                ? Errors.TITLE_LENGTH_CONSTRAINT_MIN
                : title?.length > 255
                    ? Errors.TITLE_LENGTH_CONSTRAINT_MAX
                    : "",

            description: description?.length < 11
                ? Errors.DESCRIPTION_LENGTH_CONSTRAINT_MIN
                : description?.length > 2000
                    ? Errors.DESCRIPTION_LENGTH_CONSTRAINT_MAX
                    : "",

            authorId: !(authorId)? Errors.AUTHOR_CONTRAINT_NOT_EMPTY : "",
        }
    });
}