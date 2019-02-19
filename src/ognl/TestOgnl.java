package ognl;


import ognl.Ognl;
import ognl.OgnlException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunepengwei on 2017/1/13.
 */
public class TestOgnl {

	public static void main(String[] args) {
		Person p = new Person();
		p.setName("spw");
		List<Person> l = new ArrayList<>();
		l.add(new Person());
		l.add(new Person());
		p.setPersons(l);

		for (Person temp : p.getPersons()){
			temp.setName("s1");
		}
		String  s = "EBU4418046557081";
		String s1 = "EBU4418046557081";
		System.out.println(s.equals(s1));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ss",123.34);

		System.out.println(p.getPersons().get(0).getName());

		try {
			Boolean obj1 = (Boolean) Ognl.getValue(" name != null ", p);
			System.out.println(obj1);
			Boolean obj2 = (Boolean) Ognl.getValue(" age != null ", p);
			System.out.println(obj2);
		} catch (OgnlException e) {
			//
		}
	}

	static class Person{
		private int id;

		private String name;

		private Integer age;

		private List<Person> persons;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

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

		public void setAge(Integer age) {
			this.age = age;
		}

		public List<Person> getPersons() {
			return persons;
		}

		public void setPersons(List<Person> persons) {
			this.persons = persons;
		}
	}
}
