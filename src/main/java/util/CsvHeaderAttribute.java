package util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvHeaderAttribute {

    String name() default "";

    boolean isDisplay() default true;

    boolean dynamicDisplay() default false;

    boolean international() default true;
}
