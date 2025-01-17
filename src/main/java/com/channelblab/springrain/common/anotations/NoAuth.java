package com.channelblab.springrain.common.anotations;

import java.lang.annotation.*;

/**
 * tell the system that we don't need to validate the permission of this interface
 */
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {

}