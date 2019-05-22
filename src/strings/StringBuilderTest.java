/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package strings;

/**
 *
 * @author pengwei.sun
 * @version $Id: StringBuilderTest.java, v 0.1 2019Äê03ÔÂ28ÈÕ 10:10 PM pengwei.sun Exp $
 */
public class StringBuilderTest {

    public static void main(String[] args) {
        StringBuilder stringBuilder =  new StringBuilder();

        System.out.println(stringBuilder.toString());
        System.out.println(stringBuilder.append("ssss").toString().length());
    }
}