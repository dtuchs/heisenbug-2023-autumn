package guru.qa.rococo.config;

public interface Config {

    static Config getInstance() {
        return "docker".equals(System.getProperty("test.env"))
            ? DockerConfig.INSTANCE
            : LocalConfig.INSTANCE;
    }

    String authUrl();

    String frontUrl();

    String apiUrl();

    String apiJdbcUrl();

}
