package guru.qa.rococo.jupiter.second;

import io.qameta.allure.Allure;
import io.qameta.allure.util.ResultsUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.params.provider.AnnotationBasedArgumentsProvider;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class AllureParamExtension implements LifecycleMethodExecutionExceptionHandler {
  @SuppressWarnings("unchecked")
  @Override
  public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
    Annotation dataProviderAnnotation = Arrays.stream(context.getRequiredTestMethod().getAnnotations())
        .filter(this::isAnnotationDataProvider)
        .findFirst()
        .orElse(null);

    boolean testWithAllureParam = Arrays.stream(context.getRequiredTestMethod().getParameters())
        .anyMatch(p -> AnnotationSupport.isAnnotated(p, AllureParamId.class));

    if (dataProviderAnnotation != null && testWithAllureParam) {

      Class<? extends ArgumentsProvider> handlerClass = AnnotationSupport.findAnnotation(dataProviderAnnotation.annotationType(), ArgumentsSource.class)
          .orElseThrow()
          .value();

      Constructor<? extends ArgumentsProvider> handlerConstructor = handlerClass.getDeclaredConstructor();
      handlerConstructor.setAccessible(true);
      AnnotationBasedArgumentsProvider handlerInstance = (AnnotationBasedArgumentsProvider)
          handlerConstructor.newInstance();

      int invocationIndex = Integer.parseInt(
          StringUtils.substringBetween(
              context.getUniqueId(),
              "test-template-invocation:#",
              "]"
          )
      );

      handlerInstance.accept(dataProviderAnnotation);
      handlerInstance.provideArguments(context)
          .skip(invocationIndex - 1)
          .findFirst()
          .ifPresent(
              arg -> {
                String allureId = (String) ((Arguments) arg).get()[0];
                Allure.label(ResultsUtils.ALLURE_ID_LABEL_NAME, allureId);
              }
          );
    }

    throw throwable;
  }

  private boolean isAnnotationDataProvider(Annotation annotation) {
    return AnnotationSupport.isAnnotated(annotation.annotationType(), ArgumentsSource.class);
  }
}
