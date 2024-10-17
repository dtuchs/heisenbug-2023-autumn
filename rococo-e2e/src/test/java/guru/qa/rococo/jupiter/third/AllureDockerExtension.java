package guru.qa.rococo.jupiter.third;

import guru.qa.rococo.api.allure.AllureClient;
import guru.qa.rococo.jupiter.first.SuiteExtension;
import guru.qa.rococo.model.allure.AllureResults;
import guru.qa.rococo.model.allure.DecodedAllureFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AllureDockerExtension implements SuiteExtension {

  public static final boolean inDocker = "docker".equals(System.getProperty("test.env"));
  public static final AllureClient allureClient = new AllureClient();
  public static final String projectId = "rococo";
  public static final String allurePath = "./rococo-e2e/build/allure-results";
  public static final Base64.Encoder encoder = Base64.getEncoder();

  @SneakyThrows
  @Override
  public void beforeAllTests(ExtensionContext context)  {
    if (inDocker) {
      allureClient.createProjectIfNotExist(projectId);
      allureClient.clean(projectId);
    }
  }

  @Override
  public void afterAllTests() {
    if (inDocker) {
      try (Stream<Path> paths = Files.walk(Path.of(allurePath)).filter(Files::isRegularFile)) {
        List<DecodedAllureFile> results = new ArrayList<>();
        for (Path path : paths.toList()) {
          try (InputStream is = Files.newInputStream(path)) {
            results.add(
                new DecodedAllureFile(
                    path.getFileName().toString(),
                    encoder.encodeToString(is.readAllBytes())
                )
            );
          }
        }
        allureClient.sendResultsToAllure(
            projectId,
            new AllureResults(
                results
            )
        );
        allureClient.generateReport(
            projectId
        );
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
