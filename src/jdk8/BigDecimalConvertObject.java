package jdk8;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunpengwei
 * @description BigDecimal 和Object类型互换
 * @date 2019/6/15 14:01
 */
public class BigDecimalConvertObject {

    public static void main(String[] args) {


        BigDecimal bigDecimal = new BigDecimal("1.0");

        List<BigDecimal> bigDecimalList = Lists.newArrayList(bigDecimal);

        Map<String,List<BigDecimal>> m = new HashMap<>();
        m.put("key1",bigDecimalList);

        new Thread(new Runnable() {    @Override    public void run() { throw new RuntimeException("抛出异常了");    } }).start();
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {

        }

    }
}
