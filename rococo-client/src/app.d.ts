// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces
// and what to do when importing types
declare namespace App {
	// interface Locals {}
	// interface PageData {}
	// interface Error {}
	// interface Platform {}
}

declare namespace svelteHTML {
    interface HTMLAttributes<T> {
		'on:viewportenter'?: (event: CustomEvent) => void
		'on:viewportleave'?: (event: CustomEvent) => void
    }
  }
