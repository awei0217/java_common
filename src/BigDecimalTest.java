/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */

import java.math.BigDecimal;
import java.util.Currency;

/**
 *
 * @author pengwei.sun
 * @version $Id: BigDecimalTest.java, v 0.1 2019��03��15�� 5:51 PM pengwei.sun Exp $
 */
public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal m = new BigDecimal("3");
        BigDecimal m1 = new BigDecimal("4");
        BigDecimal m2 = new BigDecimal("5");
        BigDecimal m4 = new BigDecimal("6");

        /*System.out.println(m.toString());

        String bigDecimal = BigDecimal.valueOf(0, Currency.getInstance("CNY").getDefaultFractionDigits()).setScale(0,BigDecimal.ROUND_DOWN).toString();

        System.out.println(bigDecimal);
        BigDecimal bigDecimal1 = new BigDecimal("5.2123");
        BigDecimal bigDecimal2 = new BigDecimal("-5.2123");

        BigDecimal b = bigDecimal1.add(bigDecimal2.abs()).subtract(bigDecimal2).setScale(0,BigDecimal.ROUND_CEILING);

        System.out.println(b);*/

        System.out.println(m.add(m1).add(m2).multiply(m4));

    }


}