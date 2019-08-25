package util;

public @interface CsvAttribute {
    boolean isDisplay() default true;

    boolean isMoney() default false;
}
