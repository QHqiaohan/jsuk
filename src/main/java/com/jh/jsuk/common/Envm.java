package com.jh.jsuk.common;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Envm {

    String name();

    String description() default "";

}
