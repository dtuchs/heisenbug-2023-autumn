package guru.qa.rococo.jupiter.first;

import guru.qa.rococo.config.Config;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PreconditionsExtension implements SuiteExtension {

  private static final Config CFG = Config.getInstance();

  @Override
  public void beforeAllTests(ExtensionContext context) {
    try (Connection conn = DriverManager.getConnection(
        CFG.apiJdbcUrl(),
        "root",
        "secret")) {
      ScriptUtils.executeSqlScript(
          conn,
          new EncodedResource(
              new ClassPathResource("setup.sql"),
              UTF_8
          )
      );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
