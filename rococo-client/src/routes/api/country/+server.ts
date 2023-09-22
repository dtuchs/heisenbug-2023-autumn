import { json } from '@sveltejs/kit';

import type { RequestHandler } from './$types';
import { countries } from '../../../mock/counties';

export const GET: RequestHandler = async ({ request }) => {

    return json(countries);
};