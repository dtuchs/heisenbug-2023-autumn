<script>
	import { Avatar } from '@skeletonlabs/skeleton';
</script>


<nav class="">
	<p class="text-3xl text-center m-20">Ваши любимые картины и художники всегда рядом</p>

	<ul class="grid grid-cols-1 md:grid-cols-3 gap-8 p-4 w-4/5 mx-auto">
		<li>
			<a href={`/painting`}>
				<img class="rounded-lg object-cover w-72 h-96 mx-auto" src="/piones-dtuchs.jpeg" alt="Ссылка на картины">
				<div class="text-center p-2 text-bold">Картины</div>
			</a>
		</li>
		<li>
			<a href={`/artist`}>
				<img class="rounded-lg object-cover w-72 h-96 mx-auto" src="/renuar.jpeg" alt="Ссылка на картины">
				<div class="text-center p-2 text-bold">Художники</div>
			</a>
		</li>
		<li>
			<a href={`/museum`}>
				<img class="rounded-lg object-cover w-72 h-96 mx-auto" src="/hermitage.jpg" alt="Ссылка на картины">
				<div class="text-center p-2 text-bold">Музеи</div>
			</a>
		</li>
	</ul>
</nav>



