package guru.qa.rococo.model.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import static java.nio.charset.StandardCharsets.UTF_8;

public record StringAsBytes(@Nullable String content) {

  public @Nonnull byte[] bytes() {
    return content == null ? new byte[0] : content.getBytes(UTF_8);
  }
}
