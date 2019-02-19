package jdk8;

import java.util.*;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/22
 * @描述
 * @联系邮箱
 */
public class CollectionsSortTest {

    public static void main(String[] args) {
        List <Double> fullList = new ArrayList <Double>(Arrays.asList(new Double(3.2),new Double(2.4),new Double(5.4)));

        List <Double> subList = fullList.subList(0,3);

        Collections.sort(fullList);

        System.out.println(subList.get(0));

    }
}
