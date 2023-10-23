package guru.qa.rococo.api;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.rococo.jupiter.ApiLoginExtension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AuthClient extends RestService {

  public AuthClient() {
    super(
        CFG.authUrl(),
        true,
        new CodeInterceptor()
    );
  }

  private final Auth auth = retrofit.create(Auth.class);

  public void doLogin(ExtensionContext context, String username, String password) throws IOException {
    auth.authorize(
        "code",
        "client",
        "openid",
        CFG.frontUrl() + "/authorized",
        ApiLoginExtension.getCodeChallenge(context),
        "S256"
    ).execute();

    auth.login(
        username,
        password,
        ApiLoginExtension.getCsrf()
    ).execute();

    JsonNode response = auth.token(
        "Basic " + new String(Base64.getEncoder().encode("client:secret".getBytes(UTF_8))),
        "client",
        CFG.frontUrl() + "/authorized",
        "authorization_code",
        ApiLoginExtension.getCode(context),
        ApiLoginExtension.getCodeVerifier(context)
    ).execute().body();

    ApiLoginExtension.setToken(
        context,
        response.get("id_token").asText()
    );
  }

  public void register(String username, String password) throws IOException {
    auth.requestRegForm().execute();
    auth.register(
        username,
        password,
        password,
        ApiLoginExtension.getCsrf()
    ).execute();
  }
}
