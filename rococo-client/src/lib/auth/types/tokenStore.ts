import type {OAuthToken} from "$lib/auth/types/OAuthToken";

export type TokenStorage = {
    get(): OAuthToken|null|undefined;
    set(token: OAuthToken): void;
    remove(): void;
}