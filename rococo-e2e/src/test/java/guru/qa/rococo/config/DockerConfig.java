package guru.qa.rococo.config;

public class DockerConfig implements Config {

  static final DockerConfig INSTANCE = new DockerConfig();

  private DockerConfig() {
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
