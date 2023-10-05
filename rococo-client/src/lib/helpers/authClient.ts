const BASE_URL = 'http://127.0.0.1:9000';
import {Buffer} from "buffer";

export const authClient = {
    getToken: async(url: string) => {
        const response = await fetch(`${BASE_URL}/${url}`, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
                "Authorization": `Basic ${Buffer.from("client:secret").toString("base64")}`,
            }
        });
        if (!response.ok) {
            throw new Error("Failed loading data");
        }
        return response.json();
    },
    logout: async() => {
        const response = await fetch(`${BASE_URL}/logout`, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("id_token")}`,
            }
        });
        if (!response.ok) {
            throw new Error("Failed logout");
        }
    }
}

