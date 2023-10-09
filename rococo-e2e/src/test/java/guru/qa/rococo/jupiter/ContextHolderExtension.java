package guru.qa.rococo.jupiter;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ContextHolderExtension implements BeforeEachCallback, AfterEachCallback {
  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    Holder.INSTANCE.remove();
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Holder.INSTANCE.set(context);
  }

  public enum Holder {
    INSTANCE;

    private final ThreadLocal<ExtensionContext> holder = new ThreadLocal<>();

    public void set(ExtensionContext context) {
      holder.set(context);
    }

    public ExtensionContext get() {
      return holder.get();
    }

    public void remove() {
      holder.remove();
    }
  }
}
