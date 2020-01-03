package json;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2019/9/3 9:54
 */
public class JsonTest {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Demo demo =new Demo();

        Map<String,String> map = new HashMap<>();
        map.put("12321312","dasdadee1wqwewq");
        map.put("123213122","dasdadee1wqwewq");
        map.put("1232131122","dasdadee1wqwewq");
        map.put("12321312123","dasdadee1wqwewq");
        map.put("1232131s2","dasdadee1wqwewq");
        map.put("1232131das2","dasdadee1wqwewq");
        map.put("123213fdfd12","dasdadee1wqwewq");
        map.put("12321311w12e2","dasdadee1wqwewq");
        demo.setName("1232132131231dadadasda");
        demo.setAge(12);
        demo.setMap(map);
        demo.setDemoList(Lists.newArrayList(demo,demo));

        String s = JSON.toJSONString(demo);

        System.out.println(s);

        long end = System.currentTimeMillis();

        System.out.println(end-start);

    }

    static class Demo{

        private String name;

        private int age;

        private List<Demo> demoList;


        private Map<String,String> map;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<Demo> getDemoList() {
            return demoList;
        }

        public void setDemoList(List<Demo> demoList) {
            this.demoList = demoList;
        }

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }
    }
}
