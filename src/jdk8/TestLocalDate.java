/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package jdk8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Predicate;

/**
 *
 * @author pengwei.sun
 * @version $Id: TestLocalDate.java, v 0.1 2019年03月16日 1:31 PM pengwei.sun Exp $
 */
public class TestLocalDate {


    public static void main(String[] args) {


        Predicate<String> p = o -> o.equals("test");

        System.out.println(p.test("test"));

        LocalDate localDate =  dateToLocalDate(new Date());
        Date date = localDateToDate(localDate);

        System.out.println(date);

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
        LocalDate birthMonthDay = LocalDate.of(2019,4,1);
        //根据保单的月和日组装一个日期
        LocalDate policyMonthDay = LocalDate.of(2019,4,1);

        System.out.println(policyMonthDay.isAfter(birthMonthDay)+"。。。");

        System.out.println(LocalDate.now().plusDays(-1).isBefore(LocalDate.now()));

        LocalDate  test  = LocalDate.parse("19920201",DateTimeFormatter.ofPattern("yyyyMMdd"));

        System.out.println(test.getMonthValue()+".."+test.getDayOfMonth());

        System.out.println(test.getChronology());

        System.out.println(LocalDate.now().toEpochDay() - LocalDate.now().toEpochDay());

        computerWeek();

        computeLastDate();
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

    public static void computerWeek(){
       /* TemporalField weekFields = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int startWeek = LocalDate.now().get(weekFields);
        LocalDate l =  LocalDate.now().plusYears(20).plusMonths(1).plusDays(6);
        System.out.println(l);
        int oneWeek =l.get(weekFields);
        System.out.println(startWeek - oneWeek);

        System.out.println(l.with(DayOfWeek.SATURDAY));*/
    }

    public static void computeLastDate(){

        //定义周几

        //定义退休时间
        LocalDate l =  LocalDate.now().plusYears(20).plusMonths(1).plusDays(6);

        LocalDate weekDate = l.with(DayOfWeek.SUNDAY);

        System.out.println(l);
        System.out.println(weekDate);
        //如果算出的时间在退休之后
        if(weekDate.isAfter(l)){

            weekDate = weekDate.plusWeeks(-1);
        }
        System.out.println(weekDate);
    }


}