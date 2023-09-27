<script lang="ts">
	import { MuseumIcon } from '$lib/types/Icons/Icon';
    import L, { type LatLngExpression } from 'leaflet';
    import 'leaflet/dist/leaflet.css';

    const icon = new L.Icon({
        iconUrl: MuseumIcon,
        iconSize: [40, 40],
        iconAnchor: [20, 40]
    });

    let map: L.Map | null;
    export let coords: LatLngExpression;


    function createMap(container: string | HTMLElement): L.Map{
	   let m = L.map(container, {preferCanvas: true }).setView(coords, 14);
        L.tileLayer(
            'https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png',
            {
            attribution: `&copy;<a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>,
                &copy;<a href="https://carto.com/attributions" target="_blank">CARTO</a>`,
            }
        ).addTo(m);

        return m;
    }


    function mapAction(container: string | HTMLElement) {
        map = createMap(container); 
		
		const markerLayer = L.layerGroup();
        const marker = new L.Marker(coords);

        marker.setIcon(icon);	
		markerLayer.addLayer(marker);
        markerLayer.addTo(map);
		
        return {
            destroy: () => {
                map?.removeLayer(markerLayer);
                map?.remove();
                map = null;
            }
        };
	}

</script>


<div class="map" style="height:100%;width:100%" use:mapAction />