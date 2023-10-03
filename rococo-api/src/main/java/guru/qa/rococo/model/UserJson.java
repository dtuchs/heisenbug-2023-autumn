package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.UserEntity;
import guru.qa.rococo.model.util.BytesAsString;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;

import java.util.UUID;

public record UserJson(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("username")
    String username,
    @JsonProperty("firstname")
    String firstname,
    @JsonProperty("lastname")
    String lastname,
    @JsonProperty("avatar")
    String avatar) {

  public static @Nonnull UserJson fromEntity(@Nonnull UserEntity entity) {
    return new UserJson(
        entity.getId(),
        entity.getUsername(),
        entity.getFirstname(),
        entity.getLastname(),
        new BytesAsString(
            entity.getAvatar()
        ).string()
    );
  }

  public @Nonnull UserEntity toEntity() {
    UserEntity entity = new UserEntity();
    entity.setUsername(username);
    entity.setFirstname(firstname);
    entity.setLastname(lastname);
    entity.setAvatar(
        new StringAsBytes(
            avatar
        ).bytes()
    );
    return entity;
  }
}
