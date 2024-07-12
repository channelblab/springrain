package com.channelblab.springrain.common.anotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {

}