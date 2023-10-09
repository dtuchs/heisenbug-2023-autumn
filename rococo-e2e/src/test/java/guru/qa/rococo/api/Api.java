package guru.qa.rococo.api;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

  @POST("/api/museum")
  Call<MuseumJson> createMuseum(@Header("Cookie") String jsessionId,
                                @Header("Authorization") String bearerToken,
                                @Body MuseumJson museum);

  @POST("/api/artist")
  Call<ArtistJson> createArtist(@Header("Cookie") String jsessionId,
                                @Header("Authorization") String bearerToken,
                                @Body ArtistJson artist);

  @POST("/api/painting")
  Call<PaintingJson> createPainting(@Header("Cookie") String jsessionId,
                                    @Header("Authorization") String bearerToken,
                                    @Body PaintingJson painting);
}
