package guru.qa.rococo.api;

import guru.qa.rococo.jupiter.ApiLoginExtension;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ApiClient extends RestService {

  public ApiClient() {
    super(
        "http://127.0.0.1:8080",
        false
    );
  }

  private final Api api = retrofit.create(Api.class);

  @SneakyThrows
  public MuseumJson createMuseum(ExtensionContext context, MuseumJson museumJson) {
    return api.createMuseum(
        ApiLoginExtension.getJsessionIdCookieAsString(),
        ApiLoginExtension.getBearerToken(context),
        museumJson
    ).execute().body();
  }

  @SneakyThrows
  public ArtistJson createArtist(ExtensionContext context, ArtistJson artistJson) {
    return api.createArtist(
        ApiLoginExtension.getJsessionIdCookieAsString(),
        ApiLoginExtension.getBearerToken(context),
        artistJson
    ).execute().body();
  }

  @SneakyThrows
  public PaintingJson createPainting(ExtensionContext context, PaintingJson paintingJson) {
    return api.createPainting(
        ApiLoginExtension.getJsessionIdCookieAsString(),
        ApiLoginExtension.getBearerToken(context),
        paintingJson
    ).execute().body();
  }
}
