package ognl;


import ognl.Ognl;
import ognl.OgnlException;

/**
 * @author sunepengwei on 2017/1/13.
 */
public class TestOgnl {

	public static void main(String[] args) {
		Person p = new Person();
		p.setName("spw");

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
	}
}
