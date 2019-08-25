package util;

public @interface CsvHeaderAttribute {

    String name() default "";

    boolean isDisplay() default true;

    boolean dynamicDisplay() default false;

    boolean international() default true;

    boolean isMoney() default false;
}
