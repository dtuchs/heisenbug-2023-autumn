package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.ArtistEntity;
import jakarta.annotation.Nonnull;

import java.util.UUID;

public record ArtistJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("firstname")
    String firstname,
    @JsonProperty("lastname")
    String lastname,
    @JsonProperty("photo")
    byte[] photo) {

  public static @Nonnull ArtistJson fromEntity(@Nonnull ArtistEntity entity) {
    return new ArtistJson(
        entity.getId(),
        entity.getFirstname(),
        entity.getLastname(),
        entity.getPhoto()
    );
  }

  public @Nonnull ArtistEntity toEntity() {
    ArtistEntity entity = new ArtistEntity();
    entity.setFirstname(firstname);
    entity.setLastname(lastname);
    entity.setPhoto(photo);
    return entity;
  }
}
