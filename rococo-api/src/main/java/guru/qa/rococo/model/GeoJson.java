package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeoJson(
    @JsonProperty("city") String city,
    @JsonProperty("country") CountryJson country
) {
}
