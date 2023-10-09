package guru.qa.rococo.api;

import guru.qa.rococo.jupiter.ApiLoginExtension;
import guru.qa.rococo.jupiter.ContextHolderExtension;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class CodeInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    final Response response = chain.proceed(chain.request());
    if (response.isRedirect() && response.header("Location").contains("code=")) {
      ApiLoginExtension.setCode(ContextHolderExtension.Holder.INSTANCE.get(), StringUtils.substringAfter(
          response.header("Location"), "code="
      ));
    }
    return response;
  }
}
