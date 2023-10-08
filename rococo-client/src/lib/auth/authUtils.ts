import * as crypto from "crypto-js";
import sha256 from "crypto-js/sha256";
import Base64 from "crypto-js/enc-base64";
import {sessionStore} from "$lib/stores/sessionStore";
import {goto} from "$app/navigation";

const base64Url = (str: string | crypto.lib.WordArray) => {
    return str.toString(Base64).replace(/=/g, "").replace(/\+/g, "-").replace(/\//g, "_");
}

const generateCodeVerifier = () => {
    return base64Url(crypto.enc.Base64.stringify(crypto.lib.WordArray.random(32)));
}

const generateCodeChallenge = () => {
    const codeVerifier = localStorage.getItem("codeVerifier");
    return base64Url(sha256(codeVerifier!));
}

const getAuthLink = (codeChallenge: string) => {
    return `${import.meta.env.VITE_AUTH_URL}/oauth2/authorize?response_type=${import.meta.env.VITE_RESPONSE_TYPE}&client_id=${import.meta.env.VITE_CLIENT_ID}&scope=openid&redirect_uri=${import.meta.env.VITE_FRONT_URL}/authorized&code_challenge=${codeChallenge}&code_challenge_method=S256`
}

const getTokenUrl = (code: string, verifier: string) => {
    return `oauth2/token?client_id=${import.meta.env.VITE_CLIENT_ID}&redirect_uri=${import.meta.env.VITE_FRONT_URL}/authorized&grant_type=authorization_code&code=${code}&code_verifier=${verifier}`;
}

const initLocalStorageAndRedirectToAuth = async () => {
    const codeVerifier = generateCodeVerifier();
    localStorage.setItem('codeVerifier', codeVerifier);
    const codeChallenge = generateCodeChallenge();
    localStorage.setItem('codeChallenge', codeChallenge);

    const link = getAuthLink(codeChallenge);
    await goto(link);
}

const clearSession = () => {
    localStorage.removeItem('codeVerifier');
    localStorage.removeItem('codeChallenge');
    localStorage.removeItem('id_token');
    sessionStore.update((prevState) => {
        return{
            ...prevState,
            user: undefined,
        }
    });
}

export {generateCodeChallenge, generateCodeVerifier, getAuthLink, getTokenUrl, clearSession, initLocalStorageAndRedirectToAuth};