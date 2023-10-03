package guru.qa.rococo.model;

import jakarta.annotation.Nonnull;

import java.util.Date;
import java.util.List;

public record ErrorJson(@Nonnull Date timestamp,
                        int status,
                        @Nonnull List<String> errors) {
}
