package guru.qa.rococo.api.gh;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.rococo.api.RestService;

import java.io.IOException;

public class GhApiClient extends RestService {

  public GhApiClient() {
    super("https://api.github.com/", false);
  }

  private final GhApi ghApi = retrofit.create(GhApi.class);

  public String getIssueState(String issueNumber) throws IOException {
    JsonNode responseBody = ghApi.issue(
        "Bearer " + System.getenv("GITHUB_TOKEN"),
        issueNumber
    ).execute().body();
    return responseBody.get("state").asText();
  }
}
