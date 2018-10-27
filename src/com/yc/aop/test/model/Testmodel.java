package com.yc.aop.test.model;

public class Testmodel {

	public Testmodel() {
	}
	
	public Testmodel speak(String str) {
		System.out.println(str);
		return null;
	}
	
	public Testmodel talk() {
		System.out.println("无参方法");
		return null;
	}
	
	public void say() {
		System.out.println("无参无返回值方法");
	}

}
