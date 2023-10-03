package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.model.util.BytesAsString;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

public record ArtistJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("name")
    String name,
    @JsonProperty("biography")
    String biography,
    @JsonProperty("photo")
    String photo) {

  public static @Nullable ArtistJson fromEntity(@Nullable ArtistEntity entity) {
    return (entity != null)
        ? new ArtistJson(
        entity.getId(),
        entity.getName(),
        entity.getBiography(),
        new BytesAsString(
            entity.getPhoto()
        ).string()
    ) : null;
  }

  public @Nonnull ArtistEntity toEntity() {
    ArtistEntity entity = new ArtistEntity();
    entity.setName(name);
    entity.setBiography(biography);
    entity.setPhoto(
        new StringAsBytes(
            photo
        ).bytes()
    );
    return entity;
  }
}
