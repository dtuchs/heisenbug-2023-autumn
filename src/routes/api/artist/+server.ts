import { json } from '@sveltejs/kit';

import type { RequestHandler } from './$types';
import { artists } from '../../../mock/artists';

export const GET: RequestHandler = async ({ request }) => {
	const urlString = await request.url;
    const searchUrl = new URL(urlString);

    const search = searchUrl.searchParams.get("search");
    if(search) {
        return json(artists.filter((artist) => artist.name.includes(search)));
    } 
    return json(artists);
};