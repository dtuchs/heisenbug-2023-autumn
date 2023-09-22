import { user } from "../mock/user";
import type { LayoutServerLoad } from "./$types";

export const load: LayoutServerLoad = () => {
	return {
		user,
	};
};