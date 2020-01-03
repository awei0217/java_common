package serialize;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2020/1/3 10:29
 */

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import org.msgpack.MessagePack;
import tools.JsonUtils;
import java.io.IOException;
import java.util.Date;
import java.util.List;
public class Person {

    private String name;

    private long age;

    private long sex;

    private List<String> like;

    private List<Person> children;

    private List<String> phone;

    private String card;

    private String qq;
    private String weChat;

    private Date birthday;

    private double money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public long getSex() {
        return sex;
    }

    public void setSex(long sex) {
        this.sex = sex;
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public static void main(String[] args) {
        Person person = new Person();

        person.setAge(27);
        person.setName("孙朋伟");
        person.setSex(1);
        person.setLike(Lists.newArrayList("读书","打球","运动"));
        person.setCard("610525199202171334");
        person.setBirthday(new Date());
        person.setMoney(10.98);
        person.setPhone(Lists.newArrayList("18091772264"));
        person.setQq("1024886119");
        person.setWeChat("819299200212");

        Person children1 = new Person();
        children1.setAge(27);
        children1.setName("孙朋伟");
        children1.setSex(1);
        children1.setLike(Lists.newArrayList("读书","打球","运动"));
        children1.setCard("610525199202171334");
        children1.setBirthday(new Date());
        children1.setMoney(10.98);
        children1.setPhone(Lists.newArrayList("18091772264"));

        Person children2 = new Person();
        children2.setAge(27);
        children2.setName("孙朋伟");
        children2.setSex(1);
        children2.setLike(Lists.newArrayList("读书","打球","运动"));
        children2.setCard("610525199202171334");
        children2.setBirthday(new Date());
        children2.setMoney(10.98);
        children2.setPhone(Lists.newArrayList("18091772264"));


        Person children3= new Person();
        children3.setAge(27);
        children3.setName("孙朋伟");
        children3.setSex(1);
        children3.setLike(Lists.newArrayList("读书","打球","运动"));
        children3.setCard("610525199202171334");
        children3.setBirthday(new Date());
        children3.setMoney(10.98);
        children3.setPhone(Lists.newArrayList("18091772264"));


        person.setChildren(Lists.newArrayList(children1,children2,children3));
        MessagePack msgpack = new MessagePack();
        msgpack.register(Person.class);
        long start = System.nanoTime();
        for (int i=0;i<100000;i++){
//             String fs =  JSONObject.toJSONString(person);   //45000 ns   714
//             System.out.println(fs.getBytes().length);

             String gs = JsonUtils.gsonToJson(person);//470000  746

//            try {
//                byte[] bytes = msgpack.write(person);  //110000 ns   374
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        long end = System.nanoTime();

        System.out.println((end-start) / 10000);
    }
}


