import { json } from '@sveltejs/kit';

import type { RequestHandler } from './$types';
import { museums } from '../../../mock/museums';

export const GET: RequestHandler = async ({ request }) => {
	const urlString = await request.url;
    const searchUrl = new URL(urlString);

    const search = searchUrl.searchParams.get("search");
    if(search) {
        return json(museums.filter((museum) => museum.name.includes(search)));
    } 
    return json(museums);
};