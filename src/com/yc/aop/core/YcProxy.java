package com.yc.aop.core;

import java.lang.reflect.Method;
import java.util.List;

import com.yc.aop.Intercepter.core.IntercepterFactory;
import com.yc.aop.Intercepter.core.IntercepterMethodDefination;
import com.yc.aop.Intercepter.core.IntercepterTargetDefination;

public class YcProxy {
	private Object proxy;

	public YcProxy() {
	}

	public Object getProxy() {
		return proxy;
	}

	public void setProxy(Object proxy) {
		this.proxy = proxy;
	}

	boolean doBefore(Class<?> klass, Method method, Object[] args) {
		IntercepterFactory intercepterFactory = new IntercepterFactory();
		IntercepterTargetDefination itd = new IntercepterTargetDefination(klass, method);
		List<IntercepterMethodDefination> imdList = intercepterFactory.getBeforeIntercepter(itd);
		if (imdList == null) {
			return true;
		}

		for (IntercepterMethodDefination imd : imdList) {
			Method intercepterMethod = imd.getMethod();
			Object intercepterObject = imd.getObject();
			Boolean result = null;
			try {
				result = (Boolean) intercepterMethod.invoke(intercepterObject, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(!result) {
				return false;
			}
		}
		
		return true;
	}
	
	Object doAfter(Class<?> klass, Method method, Object result) {
		IntercepterFactory intercepterFactory = new IntercepterFactory();
		IntercepterTargetDefination itd = new IntercepterTargetDefination(klass, method);
		List<IntercepterMethodDefination> imdList = intercepterFactory.getAfterIntercepter(itd);
		if (imdList == null) {
			return result;
		}

		for (IntercepterMethodDefination imd : imdList) {
			Method intercepterMethod = imd.getMethod();
			Object intercepterObject = imd.getObject();
			try {
				result = intercepterMethod.invoke(intercepterObject, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	void doException(Class<?> klass, Method method, Throwable throwable) {
		IntercepterFactory intercepterFactory = new IntercepterFactory();
		IntercepterTargetDefination itd = new IntercepterTargetDefination(klass, method);
		List<IntercepterMethodDefination> imdList = intercepterFactory.getExceptionIntercepter(itd);
		if (imdList == null) {
			return;
		}

		for (IntercepterMethodDefination imd : imdList) {
			Method intercepterMethod = imd.getMethod();
			Object intercepterObject = imd.getObject();
			try {
				intercepterMethod.invoke(intercepterObject, throwable);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
