package thread;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2019/11/11 10:43
 */
public class ThreadLocalTest {



    /**
     * 本地线程变量
     */
    private static final ThreadLocal<Demo> mapThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Demo d = new Demo();
        d.name = "spw";
        mapThreadLocal.set(d );
        for ( int i=0;i<1000000;i++) {
            new BigDecimal("1111111111111111");
        }
        System.gc();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Stream.of(1,2,3,4,5,6,7,8,9,12,3,3123,213,121,23,13,123,11,31,3123,1231,3123,13123,13,213,213,213,21321).parallel().forEach( i ->{
            System.out.println(i);
            Demo m = mapThreadLocal.get();
            if(m == null){
                System.out.println("对象为空了");
            }
            if (m != null){
                if (StringUtils.isBlank(m.name)){
                    System.out.println("值为空了");
                }
            }

        });
        System.out.println("结束了");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Demo{
        private String name;
    }
}
