package guru.qa.rococo.jupiter;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface SuiteExtension extends BeforeAllCallback {

  @Override
  default void beforeAll(ExtensionContext context) throws Exception {
    context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
        .getOrComputeIfAbsent(this.getClass(),
            k -> {
              beforeAllTests(context);
              return new ExtensionContext.Store.CloseableResource() {
                @Override
                public void close() throws Throwable {
                  afterAllTests();
                }
              };
            });
  }

  default void beforeAllTests(ExtensionContext context) {

  }

  default void afterAllTests() {

  }
}
