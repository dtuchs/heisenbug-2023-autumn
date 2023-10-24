<script lang="ts">

    import {getToastStore, type ToastSettings} from "@skeletonlabs/skeleton";

    const toastStore = getToastStore();

    const triggerErrorToaster = (message: string) => {
        const t: ToastSettings = {
            message: message,
            background: 'variant-filled-error',
            classes: "break-all",
        };
        toastStore.trigger(t);
    }

    const triggerSuccessToaster = (message: string) => {
        const t: ToastSettings = {
            message: message,
            background: 'variant-filled-primary',
            classes: "break-all",
        };
        toastStore.trigger(t);
    }
</script>

<slot triggerError={triggerErrorToaster} triggerSuccess={triggerSuccessToaster}/>
