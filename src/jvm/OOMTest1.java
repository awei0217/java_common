package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/11/27
 * @描述
 * @联系邮箱
 */
public class OOMTest1 {


    /**
     * 测试堆内存移除OOM
     * -XX:NewSize 和 -Xmn(-XX:MaxNewSize)：指定JVM启动时分配的新生代内存和新生代最大内存
     * -XX:SurvivorRatio：设置新生代中1个Eden区与1个Survivor区的大小比值。在hotspot虚拟机中，新生代 = 1个Eden + 2个Survivor。如果新生代内存是10M，SurvivorRatio=8，那么Eden区占8M，2个Survivor区各占1M。
     * -XX:NewRatio：指定老年代/新生代的堆内存比例。在hotspot虚拟机中，堆内存 = 新生代 + 老年代。如果-XX:NewRatio=4表示年轻代与年老代所占比值为1:4,年轻代占整个堆内存的1/5。在设置了-XX:MaxNewSize的情况下，-XX:NewRatio的值会被忽略，老年代的内存=堆内存 - 新生代内存。老年代的最大内存 = 堆内存 - 新生代 最大内存。
     * -XX:OldSize：设置JVM启动分配的老年代内存大小，类似于新生代内存的初始大小-XX:NewSize。
     *
     * https://blog.csdn.net/liuxiao723846/article/details/72811678  -verbose:gc 参考这个解释
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/error
     * @param args
     */
    public static void main(String[] args) {
        List<Demo> list = new ArrayList<>();

        while (true){
            list.add(new Demo());
        }
    }

    static class Demo{

        private String name;

        private Integer age;

        private String no;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }
    }
}
