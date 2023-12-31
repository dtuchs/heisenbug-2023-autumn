package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.model.util.BytesAsString;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;

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

  public static @Nonnull PaintingJson fromEntity(@Nonnull PaintingEntity entity) {
    return new PaintingJson(
        entity.getId(),
        entity.getTitle(),
        entity.getDescription(),
        new BytesAsString(
            entity.getContent()
        ).string(),
        MuseumJson.fromEntity(entity.getMuseum()),
        ArtistJson.fromEntity(entity.getArtist())
    );
  }

  public @Nonnull PaintingEntity toEntity() {
    PaintingEntity entity = new PaintingEntity();
    entity.setTitle(title);
    entity.setDescription(description);
    entity.setContent(
        new StringAsBytes(
            content
        ).bytes()
    );
    return entity;
  }
}
