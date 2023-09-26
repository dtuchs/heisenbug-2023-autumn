package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.UserEntity;
import jakarta.annotation.Nonnull;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

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
        new String(entity.getAvatar(), UTF_8)
    );
  }

  public @Nonnull UserEntity toUnlinkedEntity() {
    UserEntity entity = new UserEntity();
    entity.setUsername(username);
    entity.setFirstname(firstname);
    entity.setLastname(lastname);
    entity.setAvatar(avatar.getBytes(UTF_8));
    return entity;
  }
}
