package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PaintingJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("title")
    String title,
    @JsonProperty("description")
    String description,
    @JsonProperty("content")
    String content,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("museum")
    MuseumJson museum,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("artist")
    ArtistJson artist) {
}
