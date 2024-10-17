package guru.qa.rococo.jupiter.third;

import guru.qa.rococo.api.allure.AllureClient;
import guru.qa.rococo.jupiter.first.SuiteExtension;
import guru.qa.rococo.model.allure.AllureResults;
import guru.qa.rococo.model.allure.DecodedAllureFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

public class AllureExtension implements SuiteExtension {

  private static final boolean inDocker = "docker".equals(System.getProperty("test.env"));

  private static final Base64.Encoder encoder = Base64.getEncoder();
  private static final String allurePath = "./rococo-e2e/build/allure-results";
  private static final String projectId = "rococo";
  private static final AllureClient allureApi = new AllureClient();

  @SneakyThrows
  @Override
  public void beforeAllTests(ExtensionContext context) {
    if (inDocker) {
      allureApi.createProjectIfNotExist(projectId);
      allureApi.clean(projectId);
    }
  }

  @Override
  public void afterAllTests() {
    if (inDocker) {
      List<DecodedAllureFile> results = new ArrayList<>();

      try (Stream<Path> paths = Files.walk(Path.of(allurePath)).filter(Files::isRegularFile)) {

        for (Path path : paths.toList()) {
          try (InputStream is = Files.newInputStream(path)) {
            results.add(
                new DecodedAllureFile(
                    path.getFileName().toString(),
                    encoder.encodeToString(
                        is.readAllBytes()
                    )
                )
            );
          }
        }
        allureApi.sendResultsToAllure(
            projectId,
            new AllureResults(
                results
            )
        );
        allureApi.generateReport(projectId);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }


    }
  }
}
