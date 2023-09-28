package guru.qa.rococo.service.cors;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsCustomizer {

  private final String frontUri;
  private final String authUri;

  @Autowired
  public CorsCustomizer(@Value("${rococo-ui.uri}") String frontUri,
                        @Value("${rococo-auth.uri}") String authUri) {
    this.frontUri = frontUri;
    this.authUri = authUri;
  }

  public void corsCustomizer(@Nonnull HttpSecurity http) throws Exception {
    http.cors(c -> {
      CorsConfigurationSource source = s -> {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowCredentials(true);
        cc.setAllowedOrigins(List.of(frontUri, authUri));
        cc.setAllowedHeaders(List.of("*"));
        cc.setAllowedMethods(List.of("*"));
        return cc;
      };
      c.configurationSource(source);
    });
  }
}
