package com.yc.aop.test.intercepter;

import com.yc.aop.Intercepter.annotation.After;
import com.yc.aop.Intercepter.annotation.Aspect;
import com.yc.aop.Intercepter.annotation.Before;
import com.yc.aop.test.model.Testmodel;

@Aspect
public class TestLogger {

	public TestLogger() {
	}

	@Before(klass = Testmodel.class, method = "speak")
	public boolean beforeIntercepter(String str) {
		System.out.println("带参前置拦截成功！！！");
		
		return true;
	}
	
	@After(klass = Testmodel.class,
			method = "speak", 
			parametersTypes = { String.class })
	public Testmodel afterIntercepter(Object result) {
		System.out.println("带参滞后拦截成功！！！");
		
		return null;
	}
	
	@Before(klass = Testmodel.class, method = "talk")
	public boolean beforeIntercepter2() {
		System.out.println("无参前置拦截成功！！！");
		
		return true;
	}
	
	@After(klass = Testmodel.class,
			method = "talk", 
			parametersTypes = {})
	public Testmodel afterIntercepter2(Object result) {
		System.out.println("无参后置拦截成功！！！");
		
		return null;
	}
	
	@Before(klass = Testmodel.class, method = "say")
	public boolean beforeIntercepter3() {
		System.out.println("无参无返回值前置拦截成功！！！");
		
		return true;
	}
	
	@After(klass = Testmodel.class,
			method = "say", 
			parametersTypes = {})
	public void afterIntercepter3(Object result) {
		System.out.println("无参无返回值后置拦截成功！！！");
		
	}
}
