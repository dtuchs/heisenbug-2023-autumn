package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.model.util.BytesAsString;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;

import java.util.UUID;

public record MuseumJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("title")
    String title,
    @JsonProperty("address")
    String address,
    @JsonProperty("photo")
    String photo) {

  public static @Nonnull MuseumJson fromEntity(@Nonnull MuseumEntity entity) {
    return new MuseumJson(
        entity.getId(),
        entity.getTitle(),
        entity.getAddress(),
        new BytesAsString(
            entity.getPhoto()
        ).string()
    );
  }

  public @Nonnull MuseumEntity toEntity() {
    MuseumEntity entity = new MuseumEntity();
    entity.setTitle(title);
    entity.setAddress(address);
    entity.setPhoto(
        new StringAsBytes(
            photo
        ).bytes()
    );
    return entity;
  }
}
