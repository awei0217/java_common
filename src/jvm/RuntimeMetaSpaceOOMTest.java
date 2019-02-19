package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/11/27
 * @描述
 * @联系邮箱
 */
public class RuntimeMetaSpaceOOMTest {

    /**
     * https://www.cnblogs.com/dennyzhangdd/p/6770188.html
     * jdk8 元数据区 Metaspace oom异常测试 ，在jdk8中 Metaspace  不再属于java堆，二是在本地内存中（也叫直接内存） 主要用于存储类的信息、常量池、方法数据、方法代码
     * -XX:MetaspaceSize=1M -XX:MaxMetaspaceSize=1M
     * @param args
     */

    // String.interm()
    public static void main(String[] args) {
        //使用list保持常量池的引用，避免FullGC回收常量池的行为
        List<String> list = new ArrayList<>();
        int i =0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }

    }
}
