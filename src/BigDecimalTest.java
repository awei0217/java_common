/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */

import java.math.BigDecimal;
import java.util.Currency;

/**
 *
 * @author pengwei.sun
 * @version $Id: BigDecimalTest.java, v 0.1 2019Äê03ÔÂ15ÈÕ 5:51 PM pengwei.sun Exp $
 */
public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal m = new BigDecimal("2.13");

        System.out.println(m.toString());

        String bigDecimal = BigDecimal.valueOf(0, Currency.getInstance("CNY").getDefaultFractionDigits()).setScale(0,BigDecimal.ROUND_DOWN).toString();

        System.out.println(bigDecimal);
    }
}