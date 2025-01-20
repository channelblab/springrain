package com.channelblab.springrain.common.anotations;

import java.lang.annotation.*;

/**
 * tell the system that we don't  need to log this interface
 */
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLog {
    /**
     * what kind of fields we don't need to do log,if empty the whole methods don't do log
     * @return
     */
    String[] fields() default {};
}