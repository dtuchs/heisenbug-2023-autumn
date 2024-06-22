package guru.qa.rococo.api.allure;

import guru.qa.rococo.model.allure.AllureProject;
import guru.qa.rococo.model.allure.AllureResults;
import guru.qa.rococo.api.ApiClient;
import guru.qa.rococo.api.RestService;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class AllureDockerApiClient extends RestService {

    private static final Logger LOG = LoggerFactory.getLogger(AllureDockerApiClient.class);
    private final AllureDockerApi allureDockerApi;

    public AllureDockerApiClient() {
        super(Optional.ofNullable(System.getenv("ALLURE_DOCKER_API")).orElse("http://127.0.0.1:5050/"), false);
        this.allureDockerApi = retrofit.create(AllureDockerApi.class);
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
