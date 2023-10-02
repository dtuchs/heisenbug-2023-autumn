import type {TokenStorage} from "$lib/auth/types/tokenStore";
import  {localStorage} from "$lib/auth/localStorage";

export const clientSide =  {
    redirect(url: string): Promise<void> {
        window.location.href = url;
        return Promise.resolve()
    },
    query(): Promise<URLSearchParams> {
        return Promise.resolve(new URL(window.location.href).searchParams)
    },
    fetch(uri: string, options?: Record<string, unknown>): Promise<Response> {
        return fetch(uri, options)
    },
    tokenStorage(): Promise<TokenStorage> {
        return Promise.resolve(localStorage)
    },
}