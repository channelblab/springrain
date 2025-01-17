package com.channelblab.springrain.common.anotations;

import java.lang.annotation.*;

/**
 * let system know what kind of fields we don't need to log it
 */
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogFields {
    String[] fields() default {};
}