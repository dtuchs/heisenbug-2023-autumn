package guru.qa.rococo.jupiter.second;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE_USE})
@ExtendWith(GhIssueExtension.class)
public @interface DisabledByIssue {
  String value();
}
