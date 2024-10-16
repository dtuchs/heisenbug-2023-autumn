package guru.qa.rococo.jupiter.second;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class BrowserExtension implements
    BeforeEachCallback,
    AfterEachCallback,
    TestExecutionExceptionHandler,
    LifecycleMethodExecutionExceptionHandler {

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    SelenideLogger.addListener(
        "AllureSelenide",
        new AllureSelenide()
            .screenshots(true)
            .savePageSource(true)
    );
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) {
    if (WebDriverRunner.hasWebDriverStarted()) {
      Selenide.closeWebDriver();
    }
  }

  @Override
  public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
    doScreenshot();
    throw throwable;
  }

  @Override
  public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
    doScreenshot();
    throw throwable;
  }

  @Override
  public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
    doScreenshot();
    throw throwable;
  }

  private void doScreenshot() {
    if (WebDriverRunner.hasWebDriverStarted()) {
      Allure.addAttachment(
          "Screen on fail",
          new ByteArrayInputStream(
              Objects.requireNonNull(
                  Selenide.screenshot(OutputType.BYTES)
              )
          )
      );
    }
  }
}
