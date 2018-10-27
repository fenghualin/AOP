package com.yc.aop.Intercepter.core;

import java.lang.reflect.Method;

import com.mec.util.PackageScanner;
import com.yc.aop.Intercepter.annotation.After;
import com.yc.aop.Intercepter.annotation.Aspect;
import com.yc.aop.Intercepter.annotation.Before;
import com.yc.aop.Intercepter.annotation.ThrowException;
import com.yc.aop.Intercepter.exception.IntercepterParametersMismatchingException;
import com.yc.aop.Intercepter.exception.IntercepterReturnTypeMismatchingException;

/**
 * @author yc
 *
 */
public class IntercepterScanner {

	public IntercepterScanner() {
	}

	public static void intercepterScanner(String packageName) {
		new PackageScanner() {

			@Override
			public void dealClass(Class<?> klass) {
				if (!klass.isAnnotationPresent(Aspect.class)) {
					return;
				}
				try {
					Object object = klass.newInstance();
					Method[] methods = klass.getDeclaredMethods();
					for (Method method : methods) {
						if (method.isAnnotationPresent(Before.class)) {
							Before before = method.getAnnotation(Before.class);
							dealBeforeIntercepter(klass, object, method, before);
						} else if (method.isAnnotationPresent(After.class)) {
							After after = method.getAnnotation(After.class);
							dealAfterIntercepter(klass, object, method, after);
						} else if (method.isAnnotationPresent(ThrowException.class)) {
							ThrowException exception = method.getAnnotation(ThrowException.class);
							dealExceptionIntercepter(klass, object, method, exception);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.packageScan(packageName);
	}

	static void dealExceptionIntercepter(Class<?> klass, Object object, Method method, ThrowException exception)
			throws Exception {
		// 异常拦截器的返回值只能是void.class类型
		// 异常拦截器的参数类型只能是Throwable类型
		Class<?> returnType = method.getReturnType();
		if (!returnType.equals(void.class)) {
			throw new IntercepterReturnTypeMismatchingException("异常拦截器" + method + "返回值类型只能是void.class类型");
		}

		Class<?>[] parametersTypes = method.getParameterTypes();
		if (parametersTypes.length != 1 || parametersTypes[0].equals(Throwable.class)) {
			throw new IntercepterParametersMismatchingException("异常拦截器" + method + "参数类型只能是Throwable.class类型");
		}

		Class<?> targetClass = exception.klass();
		Method targetMethod = targetClass.getMethod(exception.method(), method.getParameterTypes());
		IntercepterTargetDefination itd = new IntercepterTargetDefination(targetClass, targetMethod);
		IntercepterMethodDefination imd = new IntercepterMethodDefination(klass, method, object);
		IntercepterFactory intercepterFactory = new IntercepterFactory();
		intercepterFactory.addExceptionIntercepter(itd, imd);

	}

	static void dealAfterIntercepter(Class<?> klass, Object object, Method method, After after) throws Exception {
		// 后置拦截器的返回值类型必须是Object
		// 后置拦截器的参数必须是Object。
		Class<?> InterccepterreturnType = method.getReturnType();

		Class<?> targetClass = after.klass();
		Method targetMethod = targetClass.getMethod(after.method(), after.parametersTypes());
		Class<?> targetReturnType = targetMethod.getReturnType();
		if (!InterccepterreturnType.equals(targetReturnType)) {
			throw new IntercepterReturnTypeMismatchingException(
					"置后拦截器" + method + "返回值类型只能是" + InterccepterreturnType + "类型");
		}
		IntercepterTargetDefination itd = new IntercepterTargetDefination(targetClass, targetMethod);
		IntercepterMethodDefination imd = new IntercepterMethodDefination(klass, method, object);
		IntercepterFactory intercepterFactory = new IntercepterFactory();
		intercepterFactory.addAfterIntercepter(itd, imd);
	}

	static void dealBeforeIntercepter(Class<?> klass, Object object, Method method, Before before) throws Exception {
		// 前置拦截器的返回值必须是Boolean类型
		// 前置拦截器的参数必须是Object[]类型，实际上是被拦截方法的实参。
		Class<?> returnType = method.getReturnType();
		if (!returnType.equals(boolean.class)) {
			throw new IntercepterReturnTypeMismatchingException("置前拦截器" + method + "返回值类型只能是boolean.class类型");
		}
		Class<?> targetClass = before.klass();
		Method targetMethod = targetClass.getMethod(before.method(), method.getParameterTypes());
		IntercepterTargetDefination itd = new IntercepterTargetDefination(targetClass, targetMethod);
		IntercepterMethodDefination imd = new IntercepterMethodDefination(klass, method, object);
		IntercepterFactory intercepterFactory = new IntercepterFactory();
		intercepterFactory.addBeforeIntercepter(itd, imd);
	}
}
