/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package jdk8;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author pengwei.sun
 * @version $Id: TestLocalDate.java, v 0.1 2019年03月16日 1:31 PM pengwei.sun Exp $
 */
public class TestLocalDate {

    public static void main(String[] args) {

        LocalDate localDate =  dateToLocalDate(new Date());

        System.out.println(localDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));

        localDate = localDate.plusYears(61);

        System.out.println(localDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));

        localDate = localDate.withMonth(4).withDayOfMonth(21);

        System.out.println(localDate.format(DateTimeFormatter.ofPattern("MM月dd日")));

        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfYear());


        LocalDate  birthDateLocalDate  = LocalDate.parse("19920217",DateTimeFormatter.ofPattern("yyyyMMdd"));
        //1992 + 61
        System.out.println(birthDateLocalDate.plusYears(61));


        Date d1 = new Date();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Date d2 = new Date();

        List<Date> list  = new ArrayList<>();
        d2.setMonth(12);
        d1.setYear(2010);
        Date d3 = new Date();
        d3.setYear(2080);
        list.add(d1);
        list.add(d2);

        list.add(d3);
        System.out.println(d2.compareTo(d1));

        list.sort((m1,m2) -> m2.compareTo(m1));

        list.forEach(System.out::println);

       /* localDate = localDate.plusYears(-10);

        Date dm1 = localDateToDate(localDate);

        System.out.println(dm1);*/

       localDate = localDate.plusMonths(2);


       String localDate1 = LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"));
       String localDate2 = localDate.format(DateTimeFormatter.ofPattern("MMdd"));

       System.out.println(localDate1);
       System.out.println(localDate2);



        //根据生日的月和日组装一个日期
        LocalDate birthMonthDay = LocalDate.of(0,1,1);
        //根据保单的月和日组装一个日期
        LocalDate policyMonthDay = LocalDate.of(0,1,2);

        System.out.println(policyMonthDay.isAfter(birthMonthDay));

        System.out.println(LocalDate.now().plusDays(-1).isBefore(LocalDate.now()));

        LocalDate  test  = LocalDate.parse("19920201",DateTimeFormatter.ofPattern("yyyyMMdd"));

        System.out.println(test.getMonthValue()+".."+test.getDayOfMonth());

        System.out.println(test.getChronology());

    }

    /**
     * Date 类型转换为JDK8的LocalDate
     * LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日
     * @param date
     * @return
     */
    public static final LocalDate dateToLocalDate (Date date){

        if(!Optional.ofNullable(date).isPresent()){
            return LocalDate.now();
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * JDK8的LocalDate 类型转换为 Date
     * LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日
     * @param localDate
     * @return
     */
    public static final Date localDateToDate (LocalDate localDate){

        if(!Optional.ofNullable(localDate).isPresent()){
            return new Date();
        }

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}