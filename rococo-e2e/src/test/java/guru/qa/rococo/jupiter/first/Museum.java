package guru.qa.rococo.jupiter.first;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Museum {
  boolean fake() default false;

  String title() default "";

  String description() default "";

  String photoPath() default "";

  String city() default "";

  String country() default "";

  Painting[] paintings() default {};
}
