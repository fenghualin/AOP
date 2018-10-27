package com.yc.aop.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory {
	private YcProxy ycProxy;

	ProxyFactory() {
	}
	
	YcProxy getYcProxy() {
		return ycProxy;
	}

	<T> T getCGLibProxy(Class<?> klass, Object object) {
		T proxy = cglibProxy(klass, object);
		ycProxy = new YcProxy();
		ycProxy.setProxy(proxy);
		
		return proxy;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cglibProxy(Class<?> klass, Object object) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(klass);
		enhancer.setCallback(new MethodInterceptor() {

			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
					throws Throwable {
				return doInvoke(klass, method, object, args);

			}
		});

		return (T) enhancer.create();
	}

	<T> T getJDKProxy(Class<?> klass, Object object) {
		T proxy = jdkProxy(klass, object);
		ycProxy = new YcProxy();
		ycProxy.setProxy(proxy);
		
		return proxy;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T jdkProxy(Class<?> klass, Object object) {
		ClassLoader classLoader = klass.getClassLoader();
		Class<?>[] interfaces =  klass.getInterfaces();
		InvocationHandler invocationHandler = new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return doInvoke(klass, method, object, args);
			}
		};
		
		return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
	}
	
 	Object doInvoke(Class<?> klass, Method method, Object object, Object[] args) {
		Object result = null;
		// 置前拦截
		ycProxy.doBefore(klass, method, args);
		try {
			result = method.invoke(object, args);
			// 滞后拦截
			ycProxy.doAfter(klass, method, result);
		} catch (Exception e) {
			// 异常拦截
			ycProxy.doException(klass, method, e);
			e.printStackTrace();
		}

		return result;
	}

}
