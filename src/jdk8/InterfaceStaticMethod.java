package jdk8;/*
package com.jd.practice.jdk8Test;

*/
/**
 * Created by sunpengwei on 2016/10/20.
 */


//java 8带来的另一个有趣的特性是接口可以声明（并且必须提供实现,不实现的话会报错）静态方法。例如：
public interface InterfaceStaticMethod {
	static void method1(HanShuShiInteface hanShuShiInteface){
		System.out.println("ss");
	}

}
