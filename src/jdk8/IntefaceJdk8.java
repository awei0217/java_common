package jdk8;

/**
 * Created by sunpengwei on 2016/10/20.
 */


public interface IntefaceJdk8<T> {
	void method2();
	//void method4();//加上这个方法就不是函数式接口了
	default String method1(String name){
		return "I am " + name;
	}
	default String method3(String name){
		return "I am " + name;
	}
}
