package guru.qa.rococo.api;

import guru.qa.rococo.jupiter.first.ApiLoginExtension;
import guru.qa.rococo.jupiter.first.ContextHolderExtension;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import lombok.SneakyThrows;

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
  public MuseumJson createMuseum(MuseumJson museumJson) {
    return api.createMuseum(
        ApiLoginExtension.getJsessionIdCookieAsString(),
        ApiLoginExtension.getBearerToken(ContextHolderExtension.Holder.INSTANCE.get()),
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
  public ArtistJson createArtist(ArtistJson artistJson) {
    return api.createArtist(
        ApiLoginExtension.getJsessionIdCookieAsString(),
        ApiLoginExtension.getBearerToken(ContextHolderExtension.Holder.INSTANCE.get()),
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

  @SneakyThrows
  public PaintingJson createPainting(PaintingJson paintingJson) {
    return api.createPainting(
        ApiLoginExtension.getJsessionIdCookieAsString(),
        ApiLoginExtension.getBearerToken(ContextHolderExtension.Holder.INSTANCE.get()),
        paintingJson
    ).execute().body();
  }
}
