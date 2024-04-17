package guru.qa.rococo.jupiter.second;

import guru.qa.rococo.api.gh.GhApiClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class GhIssueExtension implements ExecutionCondition {

  private final GhApiClient ghApiClient = new GhApiClient();

  @SneakyThrows
  @Override
  public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
    DisabledByIssue disabledByIssue = AnnotationSupport
        .findAnnotation(
            extensionContext.getRequiredTestMethod(), DisabledByIssue.class
        ).orElse(
            AnnotationSupport
                .findAnnotation(extensionContext.getRequiredTestClass(), DisabledByIssue.class)
                .orElse(null)
        );

    if (disabledByIssue != null) {
      String state = ghApiClient.getIssueState(disabledByIssue.value());
      return !state.equals("closed")
          ? ConditionEvaluationResult.disabled("By issue")
          : ConditionEvaluationResult.enabled("Issue closed");
    }

    return ConditionEvaluationResult.enabled("@DisabledByIssue not found");
  }
}
