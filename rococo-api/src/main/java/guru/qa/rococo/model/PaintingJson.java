package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.PaintingEntity;
import jakarta.annotation.Nonnull;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public record PaintingJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("title")
    String title,
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
        new String(entity.getContent(), UTF_8),
        MuseumJson.fromEntity(entity.getMuseum()),
        ArtistJson.fromEntity(entity.getArtist())
    );
  }

  public @Nonnull PaintingEntity toEntity() {
    PaintingEntity entity = new PaintingEntity();
    entity.setTitle(title);
    entity.setContent(content.getBytes(UTF_8));
    return entity;
  }
}
