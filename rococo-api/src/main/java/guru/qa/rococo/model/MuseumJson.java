package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.MuseumEntity;
import jakarta.annotation.Nonnull;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

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
        new String(entity.getPhoto(), UTF_8)
    );
  }

  public @Nonnull MuseumEntity toEntity() {
    MuseumEntity entity = new MuseumEntity();
    entity.setTitle(title);
    entity.setAddress(address);
    entity.setPhoto(photo.getBytes(UTF_8));
    return entity;
  }
}
