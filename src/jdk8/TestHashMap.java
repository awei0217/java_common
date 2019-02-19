package jdk8;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 *
 * ttps://www.cnblogs.com/doit8791/p/5677419.html  GitHub上那些值得一试的JAVA开源库
 * @创建人 sunpengwei
 * @创建时间 2019/1/7
 * @描述
 * @联系邮箱
 */
public class TestHashMap {
    /**
     * 测试hashmap 存储500万可以 value 占用多少内存
     * @param args
     */
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Map<String,String> map = new HashMap<>();
        for (int i=1;i< 50000;i++){
            map.put("EMG0123456789"+i,"EMG0123456789"+i);
        }

        System.out.println(map.size());
        long start = System.nanoTime();
        System.out.println(map.get("EMG0123456789"+30000));
        System.out.println(System.nanoTime()-start);

    }
}
