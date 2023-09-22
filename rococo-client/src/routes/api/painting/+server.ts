import { json } from '@sveltejs/kit';

import type { RequestHandler } from './$types';
import { paintings } from '../../../mock/paintings';

export const GET: RequestHandler = async ({ request }) => {
	const urlString = await request.url;
    const searchUrl = new URL(urlString);

    const search = searchUrl.searchParams.get("search");
    if(search) {
        return json(paintings.filter((painting) => painting.title.includes(search)));
    } 
    return json(paintings);
};