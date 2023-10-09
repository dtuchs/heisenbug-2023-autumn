package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserJson(
    @JsonProperty("username")
    String username,
    @JsonIgnore
    String password) {
}
