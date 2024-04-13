package guru.qa.rococo.jupiter.first;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.rococo.api.AuthClient;
import guru.qa.rococo.api.ThreadLocalCookieStore;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.utils.OauthUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.openqa.selenium.Cookie;

public class ApiLoginExtension implements BeforeEachCallback, AfterEachCallback {

  public static final Config CFG = Config.getInstance();

  public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);

  private final AuthClient authClient = new AuthClient();

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    ApiLogin apiLogin = AnnotationSupport
        .findAnnotation(
            context.getRequiredTestMethod(), ApiLogin.class
        ).orElse(
            AnnotationSupport
                .findAnnotation(context.getRequiredTestClass(), ApiLogin.class)
                .orElse(null)
        );

    if (apiLogin != null) {
      setCodeVerifier(context, OauthUtils.generateCodeVerifier());
      setCodeChallenge(context, OauthUtils.generateCodeChallenge(getCodeVerifier(context)));
      authClient.doLogin(context, apiLogin.username(), apiLogin.password());
      Selenide.open(CFG.frontUrl());
      Selenide.localStorage().setItem("id_token", getToken(context));
      WebDriverRunner.getWebDriver().manage().addCookie(getJsessionIdCookie());
      Selenide.refresh();
    }
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    ThreadLocalCookieStore.INSTANCE.removeAll();
  }

  public static void setCodeVerifier(ExtensionContext extensionContext, String codeVerifier) {
    extensionContext.getStore(NAMESPACE).put("cv", codeVerifier);
  }

  public static String getCodeVerifier(ExtensionContext extensionContext) {
    return extensionContext.getStore(NAMESPACE).get("cv", String.class);
  }

  public static void setCodeChallenge(ExtensionContext context, String codeChallenge) {
    context.getStore(NAMESPACE).put("cc", codeChallenge);
  }

  public static String getCodeChallenge(ExtensionContext context) {
    return context.getStore(NAMESPACE).get("cc", String.class);
  }

  public static void setCode(ExtensionContext context, String code) {
    context.getStore(NAMESPACE).put("code", code);
  }

  public static String getCode(ExtensionContext context) {
    return context.getStore(NAMESPACE).get("code", String.class);
  }

  public static void setToken(ExtensionContext context, String token) {
    context.getStore(NAMESPACE).put("token", token);
  }

  public static String getToken(ExtensionContext context) {
    return context.getStore(NAMESPACE).get("token", String.class);
  }

  public static String getBearerToken(ExtensionContext context) {
    return "Bearer " + getToken(context);
  }

  public static String getCsrf() {
    return ThreadLocalCookieStore.INSTANCE.cookieValue("XSRF-TOKEN");
  }

  public static String getJsessionid() {
    return ThreadLocalCookieStore.INSTANCE.cookieValue("JSESSIONID");
  }

  public Cookie getJsessionIdCookie() {
    return new Cookie(
        "JSESSIONID",
        getJsessionid()
    );
  }

  public static String getJsessionIdCookieAsString() {
    return "JSESSIONID=" + getJsessionid();
  }
}
