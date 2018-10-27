package com.yc.aop.Intercepter.core;

import java.lang.reflect.Method;

/**
 * @author yc
 *
 */
public class IntercepterMethodDefination {
	private Class<?> klass;
	private Method method;
	private Object object;

	IntercepterMethodDefination(Class<?> klass, Method method, Object object) {
		this.klass = klass;
		this.method = method;
		this.object = object;
	}

	Class<?> getKlass() {
		return klass;
	}

	void setKlass(Class<?> klass) {
		this.klass = klass;
	}

	public Method getMethod() {
		return method;
	}

	void setMethod(Method method) {
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	void setObject(Object object) {
		this.object = object;
	}

}
