package com.yc.aop.Intercepter.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author yc
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ThrowException {
	Class<?> klass();
	String method();
}
