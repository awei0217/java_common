package jvm;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2020/1/15 10:36
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        //测试一的输出
        System.out.println(Children1.children1);
        System.out.println("-------------------------------");
        //测试二的输出
        System.out.println(Children2.parent2);
        System.out.println("--------------------------------");
        //测试三的输出
        System.out.println(Children3.parent3);
        System.out.println("--------------------------------");
        //测试死的输出
        System.out.println(Children4.parent4);
        System.out.println("--------------------------------");

        String s1="abc";
        String s2="abc";
        System.out.println(s1==s2);//true


        String s3=new String("ab")+new String("c");
        //这里显示false，可能先在字符串常量池中添加s1，然后动态的添加s3，会显示false
        s3.intern();
        System.out.println(s1==s3); //false

        String s4=new String("ab")+new String("cd");
        //s4.intern();
        String s5="abcd";
        System.out.println(s4==s5);//true,去掉intern之后是false;
    }
}
class Parent1{
    public static String parent1 = "hello parent1";
    static { System.out.println("Parent1 静态代码块"); }
}
class Children1 extends Parent1{
    public static String children1 = "hello children1";
    static {System.out.println("Children1 静态代码块");}
}


class GrandParent2{
    static { System.out.println("GrandParent2静态代码块"); }
}
class Parent2 extends GrandParent2{
    public static String parent2="hello parent2";
    static{ System.out.println("Parent2 静态代码块");}
}
class Children2 extends Parent2{
    public static String children2 ="hello children2";
    static{ System.out.println("Children2 静态代码块");}
}

class GrandParent3{
    static { System.out.println("GrandParent3静态代码块"); }
}
class Parent3 extends GrandParent3{
    public final static String parent3="hello parent3";
    static{ System.out.println("Parent3 静态代码块");}
}
class Children3 extends Parent3{
    public static String children3 ="hello children3";
    static{ System.out.println("Children3 静态代码块");}
}

class Parent4{
    public final static String parent4="hello parent4";
}

class Children4 extends Parent4{
    static{ System.out.println("Children4 静态代码块");}
}
