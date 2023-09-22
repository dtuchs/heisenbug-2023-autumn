import type { ComponentType } from "svelte";
import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';


export function prepareModal(ref: ComponentType): ModalSettings {
    const c: ModalComponent = { ref };
    return {
        type: 'component',
        component: c,
        title: 'Новый художник',
        body: 'Заполните форму ниже, чтобы добавить художника',
        response: (r) => console.log('response:', r)
    };
}