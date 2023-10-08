<script lang="ts">
    export let link: string;
    export let src: string;
    export let altText: string;
    export let linkText: string;
</script>

<li>
    <a href={link}>
        <img class="rounded-lg object-cover w-72 h-96 mx-auto" {src} alt={altText}>
        <div class="text-center text-2xl p-6 text-bold">{linkText}</div>
    </a>
</li>