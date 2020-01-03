package jdk8;

import com.google.common.collect.Lists;

import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2019/9/5 15:50
 */
public class ListTest {

    public static void main(String[] args) {
        List<Integer> m1 = Lists.newArrayList(1,2,3);
        List<Integer> m2 = Lists.newArrayList(4,2,3);

        Integer max = Stream.of(1,2,3,4,5).max(Integer::compareTo).get();
        Integer min = Stream.of(1,2,3,4,5).min(Integer::compareTo).get();

        m1.retainAll(m2);
        System.out.println(m1.size());
        System.out.println(m2.size());

        Student student  =new Student();
        student.setList(Lists.newArrayList("1","2","3"));

        List<String> temp = student.getList();

        Iterator<String> iterator = temp.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            iterator.remove();
            break;
        }
        System.out.println(student.getList().size());


        List<Integer> list = Stream.of(1,2,1,3,5,2,8,3).distinct().collect(Collectors.toList());

        IntSummaryStatistics stats = list.stream().mapToInt(i -> i). summaryStatistics();
        int max1 = stats.getMax();        //获取最大值
        int min1 = stats.getMin();        //获取最小值
        double sum =  stats.getSum();    //获取总值
        double avg = stats.getAverage();  //获取平均值
        long count = stats.getCount();     //获取总数量

        List<Integer> temp1 = Stream.of(2,1,3,5,0,6).sorted().collect(Collectors.toList());

        List<Integer> temp2 = Stream.of(2,1,3,5,0,6)
                .sorted((x,y) -> Integer.compare(x,y))
                .collect(Collectors.toList());


    }

    static class Student{
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
