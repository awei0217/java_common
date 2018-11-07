package jdk8;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author sunpengwei
 * @创建时间 2018/11/5
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class LinkedHashMapTest {


    public static void main(String[] args) {
        Map<Integer,Integer> map = new LinkedHashMap(100,0.75f,true);

        map.put(3,1);
        map.put(2,7);
        map.put(5,4);

        for (Map.Entry entry :map.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
