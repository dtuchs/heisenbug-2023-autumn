package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.ArtistEntity;
import jakarta.annotation.Nonnull;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public record ArtistJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("name")
    String name,
    @JsonProperty("biography")
    String biography,
    @JsonProperty("photo")
    String photo) {

  public static @Nonnull ArtistJson fromEntity(@Nonnull ArtistEntity entity) {
    return new ArtistJson(
        entity.getId(),
        entity.getName(),
        entity.getBiography(),
        new String(entity.getPhoto(), UTF_8)
    );
  }

  public @Nonnull ArtistEntity toEntity() {
    ArtistEntity entity = new ArtistEntity();
    entity.setName(name);
    entity.setBiography(biography);
    entity.setPhoto(photo.getBytes(UTF_8));
    return entity;
  }
}
