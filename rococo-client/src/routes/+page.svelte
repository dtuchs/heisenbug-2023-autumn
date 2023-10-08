<script>
	import MainPageMenuItem from "$lib/components/MainPageMenuItem.svelte";
</script>


<nav>
	<p class="text-3xl text-center m-14">Ваши любимые картины и художники всегда рядом</p>

	<ul class="grid grid-cols-1 md:grid-cols-3 gap-8 p-4 w-4/5 mx-auto">
		<MainPageMenuItem
				link="/painting"
				linkText="Картины"
				src="/piones-dtuchs.jpeg"
				altText="Ссылка на картины"
		/>
		<MainPageMenuItem
				link="/artist"
				linkText="Художники"
				src="/renuar.jpeg"
				altText="Ссылка на художников"
		/>
		<MainPageMenuItem
				link="/museum"
				linkText="Музеи"
				src="/hermitage.jpg"
				altText="Ссылка на музеи"
		/>
	</ul>
</nav>



