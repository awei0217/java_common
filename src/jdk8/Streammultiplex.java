/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package jdk8;

import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pengwei.sun
 * @version $Id: Streammultiplex.java, v 0.1 2019Äê03ÔÂ28ÈÕ 12:00 PM pengwei.sun Exp $
 */
public class Streammultiplex {

    public static void main(String[] args) {
        String[] array = {"a", "b", "c", "d", "e"};

        Supplier<Stream<String>> streamSupplier = () -> Stream.of(array);

        //get new stream
        streamSupplier.get().forEach(x -> System.out.println(x));

        //get another new stream
        long count = streamSupplier.get().filter(x -> "b".equals(x)).count();
        System.out.println(count);

       Supplier< Stream<Integer>> integerStream =  () -> Stream.of(1,2,3);

        System.out.println(integerStream.get().count());
        System.out.println(integerStream.get().collect(Collectors.toList()).get(0));

    }
}