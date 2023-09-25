import type { ComponentType } from "svelte";
import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';
import { apiClient } from "./apiClient";


export function prepareModal(ref: ComponentType, title: string, body: string): ModalSettings {
    const c: ModalComponent = { ref };
    return {
        type: 'component',
        component: c,
        title,
        body,
        response: (r) => console.log('response:', r)
    };
}