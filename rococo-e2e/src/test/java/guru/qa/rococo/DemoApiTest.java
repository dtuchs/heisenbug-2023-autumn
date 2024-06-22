package guru.qa.rococo;

import guru.qa.rococo.api.ApiClient;
import guru.qa.rococo.jupiter.first.ApiLogin;
import guru.qa.rococo.jupiter.first.ApiLoginExtension;
import guru.qa.rococo.jupiter.second.Cookie;
import guru.qa.rococo.jupiter.second.DisabledByIssue;
import guru.qa.rococo.jupiter.second.Token;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.utils.ImgUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class DemoApiTest {

  @RegisterExtension
  private static final ApiLoginExtension ext =
      new ApiLoginExtension(false);

  private final ApiClient apiClient = new ApiClient();

  @ApiLogin(username = "dima", password = "12345")
  @Test
  void museumShouldBeCreated(@Cookie String cookie, @Token String token) {
    MuseumJson result = apiClient.createMuseum(
        cookie,
        token,
        new MuseumJson(
            null,
            "Лувр Абу-Даби",
            "Пожалуй, лучший музей в ОАЭ",
            ImgUtils.convertImageToBase64("img/luvr.png"),
            new GeoJson(
                "Абу-Даби",
                new CountryJson(
                    null,
                    "Объединённые Арабские Эмираты"
                )
            )
        )
    );
    Assertions.assertNotNull(result.id());
  }
}
