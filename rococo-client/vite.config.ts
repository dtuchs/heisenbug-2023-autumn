import {defineConfig, loadEnv} from 'vite';
import purgeCss from "vite-plugin-tailwind-purgecss";
import {sveltekit} from "@sveltejs/kit/vite";

export default defineConfig(({ mode }) => {
	process.env = {...process.env, ...loadEnv(mode, process.cwd())};

	return defineConfig({
		plugins: [sveltekit(), purgeCss()],
		server: {
			host: process.env.VITE_FRONT_HOST,
			port: 3000,
		},
	});
});
