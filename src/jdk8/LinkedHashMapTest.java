package jdk8;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author sunpengwei
 * @创建时间 2018/11/5
 * @描述
 * @联系邮箱
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
        intToRoman(1);

        System.out.println( fibLoop(3));
    }
    public static long fibLoop(int num) {
        if (num < 1 || num > 92)
            return 0;
        long a = 1;
        long b = 1;
        long temp;
        for (int i = 3; i <= num; i++) {
            temp = a;
            a = b;
            b += temp;
        }
        return b;
    }

        public static String intToRoman(int num) {
        Map<String,Integer> map = new LinkedHashMap();
        map.put("M",1000);
        map.put("D",500);
        map.put("C",100);
        map.put("L",50);
        map.put("X",10);
        map.put("V",5);
        map.put("I",1);

        for (Map.Entry<String,Integer> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }
        return "";
    }
}
