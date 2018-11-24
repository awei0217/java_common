package jdk8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/11/22
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class CollectionsToMap {

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        Student s1 = new Student();
        s1.setNo("1");
        s1.setName("spw");
        Student s2 = new Student();
        s2.setNo("2");
        s2.setName("spw");
        Student s3 = new Student();
        s3.setNo("3");
        s3.setName("spw");
        Student s4 = new Student();
        s4.setNo("4");
        s4.setName("spw");

        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);

        Map<String,String> map = list.stream().collect(Collectors.toMap(Student::getNo,Student::getName)); //当key重复时会报错
        /*Map<String,String> map = new HashMap<>();
        for (Student student :list){
            map.put(student.getNo(),student.getName());
        }*/
    }

    static  class Student{
        private String no;

        private String name;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
