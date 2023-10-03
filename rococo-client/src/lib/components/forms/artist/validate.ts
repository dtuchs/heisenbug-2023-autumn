import {Errors} from "$lib/types/Errors";
import {artistsFormErrorStore} from "$lib/components/forms/artist/artist-form.error.store";

export const validateForm = (
    name: string,
    biography: string) => {

    artistsFormErrorStore.update((prevState) => {
        return {
            ...prevState,
            name: name?.length < 3
                ? Errors.NAME_LENGTH_CONSTRAINT_MIN
                : name?.length > 255
                    ? Errors.NAME_LENGTH_CONSTRAINT_MAX
                    : "",
            biography: biography?.length < 11
                ? Errors.BIOGRAPHY_LENGTH_CONSTRAINT_MIN
                : biography?.length > 2000
                    ? Errors.BIOGRAPHY_LENGTH_CONSTRAINT_MAX
                    : "",
        }
    });
}