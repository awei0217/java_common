package jdk8;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/9/19
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class CollectionsGroupTest {

    public static void main(String[] args) {
        List<Integer> strs = Arrays.asList(1,2,3,4,5,6);
        List<Integer> strs1 = new ArrayList<>();

        //方式一：求和
        Optional<Integer> sum=strs.stream().reduce((x, y)->x+y);
        System.out.println("reduce 求和方式1:"+sum.get());

        //方式二：求和
        Integer sum2=strs.stream().reduce(0,(x,y)->x+y);
        System.out.println("reduce 求和方式2:"+sum2);

        //方式三：求和
        Integer sum3=strs.stream().mapToInt((x)->x).sum();
        System.out.println("mapToInt+ sum求和方式:"+sum3);

        //1.分组计数
        List<Student> list1= Arrays.asList(
                new Student(1,"one","zhao"),new Student(2,"one","qian"),new Student(3,"two","sun"));
        //1.1根据某个属性分组计数
        Map<String,Long> result1=list1.stream().collect(Collectors.groupingBy(Student::getGroupId,Collectors.counting()));
        System.out.println(result1);
        //1.2根据整个实体对象分组计数,当其为String时常使用
        Map<Student,Long> result2=list1.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println(result2);
        //1.3根据分组的key值对结果进行排序、放进另一个map中并输出
        Map<String,Long> xMap=new HashMap<>();
        result1.entrySet().stream().sorted(Map.Entry.<String,Long>comparingByKey().reversed()) //reversed不生效
                .forEachOrdered(x->xMap.put(x.getKey(),x.getValue()));
        System.out.println(xMap);

        //2.分组，并统计其中一个属性值得sum或者avg:id总和
        Map<String,Integer> result3=list1.stream().collect(
                Collectors.groupingBy(Student::getGroupId,Collectors.summingInt(Student::getId))
        );
        System.out.println(result3);

        //strs1.stream().findAny().orElseThrow(()->new RuntimeException("ss"));

        System.out.println(Optional.ofNullable(null).orElse(Arrays.asList(1,2,3)));

    }

    static class Student{
        private int id;
        private String groupId;
        private String xing;
        private String name;
        Student(int id,String groupId,String name){
            this.id = id;
            this.groupId = groupId;
            this.name =name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getXing() {
            return xing;
        }

        public void setXing(String xing) {
            this.xing = xing;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
