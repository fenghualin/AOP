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
		System.out.println("前置拦截成功！！！ : " + str);
		
		return true;
	}
	
	@After(klass = Testmodel.class,
			method = "speak", 
			parametersTypes = { String.class })
	public Testmodel afterIntercepter(Object result) {
		System.out.println("滞后拦截成功！！！");
		
		return null;
	}
	
	@Before(klass = Testmodel.class, method = "speak")
	public boolean beforeIntercepter2(String str) {
		System.out.println("第二次前置拦截成功！！！ : " + str);
		
		return true;
	}
	
	@After(klass = Testmodel.class,
			method = "speak", 
			parametersTypes = { String.class })
	public Testmodel afterIntercepter2(Object result) {
		System.out.println("第二次滞后拦截成功！！！");
		
		return null;
	}
}
