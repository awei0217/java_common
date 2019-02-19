package reflect;

import java.lang.reflect.*;
import java.util.List;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/17
 * @描述
 * @联系邮箱
 */
public class ReflactTest {

    public static void main(String[] args) {
        long l = 1L << 40L;
        System.out.println(l);
        long u = 18120113141201001L;
        System.out.println((1L << 40) / 60 / 60 / 24 / 365 /1000 );
        Student student = new Student();

        System.out.println(2 << 13);

        Method[] methods = student.getClass().getMethods();

        /**
         * 获取List的泛型
         */
        for (Method m : methods){
            if (m.getName().equals("setList")){
                Type[] types = m.getGenericParameterTypes();

                Type type = types[0];
                ParameterizedType listGenericType = (ParameterizedType) type;
                System.out.println( listGenericType.getRawType().getTypeName());
                System.out.println( listGenericType.getActualTypeArguments()[0].getTypeName());

            }

            if (m.getName().equals("getAge")){


                try {
                   Object o =  m.invoke(student,null);

                    System.out.println(o != null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ss,").append("ss,");
        System.out.println(stringBuilder.toString().substring(0,stringBuilder.length()-1));

    }


    static class Student{


        private int age;

        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
