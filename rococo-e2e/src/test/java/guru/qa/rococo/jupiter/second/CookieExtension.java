package guru.qa.rococo.jupiter.second;

import guru.qa.rococo.jupiter.first.ApiLoginExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

public class CookieExtension implements ParameterResolver {
  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return AnnotationSupport.isAnnotated(parameterContext.getParameter(), Cookie.class)
        && parameterContext.getParameter().getType().isAssignableFrom(String.class);
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return ApiLoginExtension.getJsessionIdCookieAsString();
  }
}
