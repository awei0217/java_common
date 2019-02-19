package jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/11/27
 * @描述
 * @联系邮箱
 */
public class DirectMemoryOOMTest {

    /**
     * 配置参数 -Xmx20M -XX:MaxDirectMemorySize=10M
     * @param args
     */
    public static void main(String[] args) {
        //采用unsafe 分配本机内存
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            Unsafe unsafe  = (Unsafe) field.get(null);
            while (true){
                unsafe.allocateMemory(1024 * 1024);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
