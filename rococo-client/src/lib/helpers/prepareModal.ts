import type { ComponentType } from "svelte";
import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';

export function prepareModal(ref: ComponentType, title: string, body: string): ModalSettings {
    const component: ModalComponent = { ref };
    return {
        type: 'component',
        component,
        title,
        body,
        response: (r) => console.log('response:', r)
    };
}