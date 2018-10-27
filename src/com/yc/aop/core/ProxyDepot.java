package com.yc.aop.core;

import java.util.HashMap;
import java.util.Map;

public class ProxyDepot {
	private static final Map<String, YcProxy> proxyMap = new HashMap<>();
	
	public ProxyDepot() {
	}

	public YcProxy getYcProxy(Class<?> klass) {
		return proxyMap.get(klass.getName());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<?> klass) {
//		for(YcProxy ycProxy : proxyMap.values()) {
//			System.out.println(ycProxy);
//		}
		YcProxy ycProxy = proxyMap.get(klass.getName());
		if(ycProxy == null) {
			return null;
		}
		
		return (T) ycProxy.getProxy();
	}
	
	public void createCGLibProxy(Object object) {
		cglibProxy(object.getClass(), object);
	}
	
	public void createCGLibProxy(Class<?> klass) throws Exception {
		cglibProxy(klass, klass.newInstance());
	}

	private void cglibProxy(Class<?> klass, Object object) {
		String className = klass.getName();
		YcProxy ycProxy = proxyMap.get(className);
		if(ycProxy != null) {
			return;
		}
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.getCGLibProxy(klass, object);
		proxyMap.put(className, proxyFactory.getYcProxy());
	}
	
	public void createJDKProxy(Object object) {
		JDKProxy(object.getClass(), object);
	}
	
	public void createJDKProxy(Class<?> klass) throws Exception {
		JDKProxy(klass, klass.newInstance());
	}

	private void JDKProxy(Class<?> klass, Object object) {
		String className = klass.getName();
		YcProxy ycProxy = proxyMap.get(className);
		if(ycProxy != null) {
			return;
		}
		
		ProxyFactory proxyFactory = new ProxyFactory();
		ycProxy = proxyFactory.getJDKProxy(klass, object);
		proxyMap.put(className, ycProxy);
		
		return; 
	}
}
