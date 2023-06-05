package com.flamingo.customer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/6/2 12:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    int mustRole() default 1;

    int[] anyRole() default 0;

}
