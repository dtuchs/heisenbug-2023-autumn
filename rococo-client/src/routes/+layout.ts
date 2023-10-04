import { user } from "../mock/user";
import type { LayoutLoad } from "./$types";

export const ssr = false;
export const load: LayoutLoad = () => {
	return {
		user,
	};
};