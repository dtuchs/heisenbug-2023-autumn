package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.model.util.BytesAsString;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

public record MuseumJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("title")
    String title,
    @JsonProperty("description")
    String description,
    @JsonProperty("photo")
    String photo,
    @JsonProperty("geo")
    GeoJson geo) {

  public static @Nullable MuseumJson fromEntity(@Nullable MuseumEntity entity) {
    return (entity != null)
        ? new MuseumJson(
        entity.getId(),
        entity.getTitle(),
        entity.getDescription(),
        new BytesAsString(
            entity.getPhoto()
        ).string(),
        new GeoJson(
            entity.getCity(),
            CountryJson.fromEntity(entity.getCountry())
        )
    ) : null;
  }

  public @Nonnull MuseumEntity toEntity() {
    MuseumEntity entity = new MuseumEntity();
    entity.setTitle(title);
    entity.setDescription(description);
    entity.setPhoto(
        new StringAsBytes(
            photo
        ).bytes()
    );
    entity.setCity(geo.city());
    return entity;
  }
}
