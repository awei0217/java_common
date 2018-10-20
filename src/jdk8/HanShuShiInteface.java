package jdk8;

/**
 * Created by sunpengwei on 2016/10/20.
 */

/**
 * 函数式接口
 * Java 8 引入的一个核心概念是函数式接口。如果一个接口定义个唯一一个抽象方法，那么这个接口就成为函数式接口。
 * 比如，java.lang.Runnable就是一个函数式接口，因为它只顶一个一个抽象方法:
 */
@FunctionalInterface //用来表明这个接口是一个函数式接口
public interface HanShuShiInteface {
	void method1();

	default void method2(){
		System.out.println("默认方法2");
	}
	default void method3(){
		System.out.println("默认方法3");
	}
	default void method4(){
		System.out.println("默认方法4");
	}
}
