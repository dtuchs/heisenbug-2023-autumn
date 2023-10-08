import type {ComponentType} from "svelte";
import type {ModalSettings, ModalComponent} from '@skeletonlabs/skeleton';

export function prepareModal({ ref, title, body, callback, valueAttr = {}}:
     {
         ref: ComponentType,
         title?: string,
         body?: string,
         callback?: (data: any) => void,
         valueAttr?: Object,
     }): ModalSettings {
    const component: ModalComponent = {ref};
    return {
        type: 'component',
        component,
        title,
        body,
        valueAttr,
        response: (r) => {
            if (r) {
                callback?.(r)
            }
        },
    };
}