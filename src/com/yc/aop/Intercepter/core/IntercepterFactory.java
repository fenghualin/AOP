package com.yc.aop.Intercepter.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 *
 */
public class IntercepterFactory {
	private static final Map<IntercepterTargetDefination, List<IntercepterMethodDefination>> beforeMap;
	private static final Map<IntercepterTargetDefination, List<IntercepterMethodDefination>> afterMap;
	private static final Map<IntercepterTargetDefination, List<IntercepterMethodDefination>> exceptionMap;

	static {
		beforeMap = new HashMap<>();
		afterMap = new HashMap<>();
		exceptionMap = new HashMap<>();
	}

	//添加拦截器，供三个map调用
	static void addIntercepter(Map<IntercepterTargetDefination, List<IntercepterMethodDefination>> map,
			IntercepterTargetDefination intercepterTargetDefination,
			IntercepterMethodDefination intercepterMethodDefination) {
		List<IntercepterMethodDefination> methodList = map.get(intercepterTargetDefination);
		if (methodList == null) {
			synchronized (IntercepterFactory.class) {
				if (methodList == null) {
					methodList = new ArrayList<>();
					map.put(intercepterTargetDefination, methodList);
				}
			}
		}
		methodList.add(intercepterMethodDefination);
	}

	// 添加前置拦截器
	void addBeforeIntercepter(IntercepterTargetDefination intercepterTargetDefination,
			IntercepterMethodDefination intercepterMethodDefination) {
		addIntercepter(beforeMap, intercepterTargetDefination, intercepterMethodDefination);
	}

	// 添加后置拦截器
	void addAfterIntercepter(IntercepterTargetDefination intercepterTargetDefination,
			IntercepterMethodDefination intercepterMethodDefination) {
		addIntercepter(afterMap, intercepterTargetDefination, intercepterMethodDefination);
	}

	// 添加异常拦截器
	void addExceptionIntercepter(IntercepterTargetDefination intercepterTargetDefination,
			IntercepterMethodDefination intercepterMethodDefination) {
		addIntercepter(exceptionMap, intercepterTargetDefination, intercepterMethodDefination);
	}

	// 取得前置拦截器
	public List<IntercepterMethodDefination> getBeforeIntercepter(IntercepterTargetDefination intercepterTargetDefination) {
		return beforeMap.get(intercepterTargetDefination);
	}

	// 取得后置拦截器
	public List<IntercepterMethodDefination> getAfterIntercepter(IntercepterTargetDefination intercepterTargetDefination) {
		return afterMap.get(intercepterTargetDefination);
	}

	// 取得异常拦截器
	public List<IntercepterMethodDefination> getExceptionIntercepter(IntercepterTargetDefination intercepterTargetDefination) {
		return exceptionMap.get(intercepterTargetDefination);
	}
}
