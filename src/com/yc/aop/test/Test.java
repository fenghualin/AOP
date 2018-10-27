package com.yc.aop.test;

import com.yc.aop.Intercepter.core.IntercepterScanner;
import com.yc.aop.core.ProxyDepot;
import com.yc.aop.test.model.Testmodel;

public class Test {

	public static void main(String[] args) {
		IntercepterScanner.intercepterScanner("com.yc.aop.test.intercepter");
		
		ProxyDepot depot = new ProxyDepot();
		try {
			depot.createCGLibProxy(Testmodel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Testmodel testmodel = depot.getProxy(Testmodel.class);
		testmodel.speak("有参数的");
		testmodel.talk();
		testmodel.say();
	}

}
