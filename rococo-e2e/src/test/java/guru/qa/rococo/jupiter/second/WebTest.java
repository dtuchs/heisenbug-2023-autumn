package guru.qa.rococo.jupiter.second;

import guru.qa.rococo.jupiter.first.ApiLoginExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
    ApiLoginExtension.class,
    BrowserExtension.class,
    AllureParamExtension.class
})
public @interface WebTest {
}
