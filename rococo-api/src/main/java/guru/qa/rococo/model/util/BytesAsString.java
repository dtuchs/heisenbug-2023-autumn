package guru.qa.rococo.model.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import static java.nio.charset.StandardCharsets.UTF_8;

public record BytesAsString(@Nullable byte[] content) {

  public @Nonnull String string() {
    return content == null ? "" : new String(content, UTF_8);
  }
}
