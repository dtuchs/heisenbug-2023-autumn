import type {TokenStorage} from "$lib/auth/types/tokenStore";
import type {OAuthToken} from "$lib/auth/types/OAuthToken";

const tokenCookieName = "token";

const isLocalStorageUndefined = typeof window === "undefined" || !window["localStorage"];

export const localStorage: TokenStorage = {
    get() {
        if (isLocalStorageUndefined) {
            return undefined;
        }
        const value = window.localStorage.getItem(tokenCookieName);
        return value ? JSON.parse(value) : null;
    },
    set(token : OAuthToken) {
      if(isLocalStorageUndefined) {
          return;
      }
      window.localStorage.setItem(tokenCookieName, JSON.stringify(token));
    },
    remove() {
        if (isLocalStorageUndefined) {
            return;
        }
        window.localStorage.removeItem(tokenCookieName);
    }
}