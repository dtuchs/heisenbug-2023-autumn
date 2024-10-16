package guru.qa.rococo.api.allure;

import guru.qa.rococo.api.RestService;
import guru.qa.rococo.model.allure.AllureProject;
import guru.qa.rococo.model.allure.AllureResults;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Optional;

public class AllureClient extends RestService {

  private final AllureApi allureDockerApi;

  public AllureClient() {
    super(
        Optional.ofNullable(
            System.getenv("ALLURE_DOCKER_API")
        ).orElse("http://127.0.0.1:5050/"),
        false);
    this.allureDockerApi = retrofit.create(AllureApi.class);
  }

  public void clean(String projectId) throws IOException {
    allureDockerApi.cleanResults(projectId).execute();
  }

  public void generateReport(String projectId) throws IOException {
    allureDockerApi.generateReport(
        projectId,
        System.getenv("HEAD_COMMIT_MESSAGE"),
        System.getenv("BUILD_URL"),
        System.getenv("EXECUTION_TYPE")
    ).execute();
  }

  public void sendResultsToAllure(String projectId, AllureResults allureResults) throws IOException {
    int code = allureDockerApi.uploadResults(
        projectId,
        allureResults
    ).execute().code();
    Assertions.assertEquals(200, code);
  }

  public void createProjectIfNotExist(String projectId) throws IOException {
    int code = allureDockerApi.project(
        projectId
    ).execute().code();
    if (code == 404) {
      code = allureDockerApi.createProject(new AllureProject(projectId)).execute().code();
      Assertions.assertEquals(201, code);
    } else {
      Assertions.assertEquals(200, code);
    }
  }
}