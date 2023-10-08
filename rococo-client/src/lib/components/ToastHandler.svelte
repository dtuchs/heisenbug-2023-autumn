<script lang="ts">

    import {getToastStore, type ToastSettings} from "@skeletonlabs/skeleton";

    const toastStore = getToastStore();

    const triggerErrorToaster = (message: string) => {
        const t: ToastSettings = {
            message: message,
            background: 'variant-filled-error',
        };
        toastStore.trigger(t);
    }

    const triggerSuccessToaster = (message: string) => {
        const t: ToastSettings = {
            message: message,
            background: 'variant-filled-primary',
        };
        toastStore.trigger(t);
    }
</script>

<slot triggerError={triggerErrorToaster} triggerSuccess={triggerSuccessToaster}/>
