package jdk8;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/1
 * @描述
 * @联系邮箱
 */
public class ForEachTest {

    /**
     * 测试foreach 是否并发，结果否
     * @param args
     */
    public static void main(String[] args) {
        /*Arrays.asList(1,2,3,4,5,6,7).forEach( i -> {
            boolean flag = false;
            if (i % 2 ==0) {
                flag =true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(flag);
        });*/

        List<String> list = Lists.newArrayList("1","2","3");

    }
}
