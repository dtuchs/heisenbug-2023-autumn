<script lang="ts">
    export let isBigScreen: boolean;
</script>
<nav class={`list-nav ${isBigScreen ? "hidden md:block" : "md:hidden w-full"}`}>
    <ul class={`flex items-baseline ${!isBigScreen ? "justify-around" : ""}`}>
        <li>
            <a href="/painting">
                Картины
            </a>
        </li>
        <li>
            <a href="/artist">
                Художники
            </a>
        </li>
        <li>
            <a href="/museum">
                Музеи
            </a>
        </li>
    </ul>
</nav>
