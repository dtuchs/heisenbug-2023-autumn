package guru.qa.rococo.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

public class DockerConfig implements Config {

  static final DockerConfig INSTANCE = new DockerConfig();

  private DockerConfig() {
  }

  static {
    Configuration.browserSize = "1920x1200";
    Configuration.remote = "http://selenoid:4444/wd/hub";
    Configuration.timeout = 10000;
    Configuration.browser = "chrome";
    Configuration.browserVersion = "125.0";
    Configuration.browserCapabilities = new ChromeOptions().addArguments("--no-sandbox");
  }

  @Override
  public String frontUrl() {
    return "http://client.rococo.dc";
  }

  @Override
  public String apiUrl() {
    return "http://api.rococo.dc:8080";
  }

  @Override
  public String authUrl() {
    return "http://auth.rococo.dc:9000";
  }

  @Override
  public String apiJdbcUrl() {
    return "jdbc:mysql://rococo-db:3306/rococo-api?serverTimezone=UTC";
  }
}
