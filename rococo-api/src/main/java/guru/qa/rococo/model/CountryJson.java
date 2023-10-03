package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.model.util.BytesAsString;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

public record CountryJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("name")
    String name) {

  public static @Nullable CountryJson fromEntity(@Nullable CountryEntity entity) {
    return (entity != null)
        ? new CountryJson(
        entity.getId(),
        entity.getName()
    ) : null;
  }

  public @Nonnull CountryEntity toEntity() {
    CountryEntity entity = new CountryEntity();
    entity.setName(name);
    return entity;
  }
}
