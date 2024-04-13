package guru.qa.rococo.api;

import guru.qa.rococo.jupiter.first.ApiLoginExtension;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ApiClient extends RestService {

  public ApiClient() {
    super(
        CFG.apiUrl(),
        false
    );
  }

  private final Api api = retrofit.create(Api.class);

  @SneakyThrows
  public MuseumJson createMuseum(String cookie, String token, MuseumJson museumJson) {
    return api.createMuseum(
        cookie,
        token,
        museumJson
    ).execute().body();
  }

  @SneakyThrows
  public ArtistJson createArtist(String cookie, String token, ArtistJson artistJson) {
    return api.createArtist(
        cookie,
        token,
        artistJson
    ).execute().body();
  }

  @SneakyThrows
  public PaintingJson createPainting(String cookie, String token, PaintingJson paintingJson) {
    return api.createPainting(
        cookie,
        token,
        paintingJson
    ).execute().body();
  }
}
