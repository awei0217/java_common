package tools;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @Author sunpengwei'[
 */
public class JsonUtils {


	/**
	 * 创建gson
	 */
	public static  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();
	/**
	 * 创建序列化和反序列化是跳过某些字段的gsonSkip
	 */
	private static Gson gsonSkip = gsonSkip = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						final Expose expose = fieldAttributes.getAnnotation(Expose.class);
						return expose != null && !expose.serialize();
					}
					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						final Expose expose = aClass.getAnnotation(Expose.class);
						return expose != null && !expose.serialize();
					}
				}).addDeserializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						final Expose expose = fieldAttributes.getAnnotation(Expose.class);
						return expose != null && !expose.serialize();
					}
					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						final Expose expose = aClass.getAnnotation(Expose.class);
						return expose != null && !expose.serialize();
					}
				}).create();;

	/**
	 * 把一个对象序列化
	 * @param o
	 * @return
	 */
	/**
		example:
	 	gson.gsonToJson(任意对象，可以是数组，集合)
	 */
	public static <T> String gsonToJson(T o){
		return gson.toJson(o);
	}


	/**
	 * 把一个json字符串序列化为一个对象
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	//example :反序列化组 int [] intArray = gson.fromJson(json,int[].class)
	public static <T> T gsonFromJson(String json,Class<T> clazz){
		return (T) gson.fromJson(json,clazz);
	}

	/**
	 * 把一个json字符串序列化为一个对象
	 * @param json
	 * @param type
	 * @param <T>
	 * @return
	 */
	/*  example
	    Type listType = new TypeToken<List<Person>>(){}.getType();
		List<Person> persons = gson.fromJson(json, listType);
	*/
	public static <T> T gsonFromJson(String json,Type type){
		return (T) gson.fromJson(json,type);
	}

	/**
	 * 调用此方法序列化时排除用 @Expose 标记的字段或者class
	 */
	public static <T> String gsonToJsonSkipExpose(T t){
		return gsonSkip.toJson(t);
	}

	/**
	 * 调用此方法序列化时排除用 @Expose 标记的字段或者class
	 */
	public static <T> T gsonFromJsonSkipExpose(String json,Class clazz){
		return (T) gsonSkip.fromJson(json,clazz);
	}

	/**
	 * 调用此方法序列化时排除用 @Expose 标记的字段或者class
	 */
	public static <T> T gsonFromJsonSkipExpose(String json,Type type){
		return (T) gsonSkip.fromJson(json,type);
	}




	private JsonUtils(){}


	public static void main(String[] args) {
		//序列化对象
		/*Student student = new Student("123","spw");
		String studentJson = JsonUtils.gsonToJson(student);
		System.out.println(studentJson);
		System.out.println(JsonUtils.gsonFromJson(studentJson,Student.class));
		System.out.println(JsonUtils.gsonFromJson(studentJson,new TypeToken<Student>(){}.getType()));*/


		//序列化数组
		/*Student [] arrayStudent = {new Student("123","spw"),new Student("456","qwe")};
		String studentArrayJson = JsonUtils.gsonToJson(arrayStudent);
		System.out.println(studentArrayJson);
		Student [] s = JsonUtils.gsonFromJson(studentArrayJson,Student[].class);
		System.out.println(s.length); //用数组接受
		List<Student> list = JsonUtils.gsonFromJson(studentArrayJson,new TypeToken<List<Student>>(){}.getType());//用List接受
		System.out.println(list.size());*/

		//集合的序列化和反序列化
		/*List<Student>  list = new ArrayList<Student>();
		Student student1 = new Student("123","spw");
		Student student2 = new Student("123","spw");
		list.add(student1);list.add(student2);
		String jsonList = JsonUtils.gsonToJson(list);
		List<Student> students1 = JsonUtils.gsonFromJson(jsonList,List.class); //这种方式序列化后是List 存在的是字符串,但是不会报错
		System.out.println(students1.get(0));
		List<Student> students2 = JsonUtils.gsonFromJson(jsonList,new TypeToken<List<Student>>(){}.getType()); //LinkedList 只要是List的子类  或者 父类就行
		System.out.println(students2.get(0).getName());*/

		//验证实体中的某个字段为空null
		/*Student s1 = new Student("123");
		String sJson1 = JsonUtils.gsonToJson(s1); //s1 的name字段为空,会自动过滤
		System.out.println(sJson1); //{"id":"123"}
		System.out.println(JsonUtils.gsonFromJson(sJson1,Student.class));

		//如果不想过滤，需要这样操作
		Gson gson  = new GsonBuilder().serializeNulls().create();
		String sJson2 = gson.toJson(s1);
		System.out.println(sJson2); //{"id":"123","name":null}
		System.out.println(JsonUtils.gsonFromJson(sJson2,Student.class).getName());
		Student student = new Student("123");*/


		//序列化反序列时过滤某些字段
		/*GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		//builder.serializeSpecialFloatingPointValues();
		builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
				final Expose expose = fieldAttributes.getAnnotation(Expose.class);
				return expose != null && !expose.serialize();
			}

			@Override
			public boolean shouldSkipClass(Class<?> aClass) {
				final Expose expose = aClass.getAnnotation(Expose.class);
				return expose != null && !expose.serialize();
			}
		}).addDeserializationExclusionStrategy(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
				final Expose expose = fieldAttributes.getAnnotation(Expose.class);
				return expose != null && !expose.deserialize();
			}

			@Override
			public boolean shouldSkipClass(Class<?> aClass) {
				return false;
			}
		});

		Gson gson = builder.create();
		String str = gson.toJson(new Student("123","spw",new Date(),12.34));
		System.out.println(str);
		System.out.println(gson.fromJson(str,Student.class).getDb());*/

		//创建的Gson实例将排除未标记有@Expose注释的类中的所有字段
		/*Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String str = gson.toJson(new Student("123","spw",new Date(),12.34));
		System.out.println(str);
		System.out.println(gson.fromJson(str,Student.class).getName());*/


	}

	/**
	 *   数组序列化和反序列化
		 Array Examples
		 int[] ints = {1, 2, 3, 4, 5};
		 (Serialization) 序列化
		 gson.toJson(ints);     ==> prints [1,2,3,4,5]

		 (Deserialization) 反序列化
		 int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class);
	 	 我们还支持多维数组，它具有任意复杂的元素t
	 */


	/**
	 	 集合的序列化和反序列化
		 Collections Examples
		 Collection<Integer> ints = Lists.immutableList(1,2,3,4,5);
		 (Serialization)序列化
		 String json = gson.toJson(ints); ==> json is [1,2,3,4,5]

		 (Deserialization)反序列化
		 Type collectionType = new TypeToken<Collection<Integer>>(){}.getType();
		 Collection<Integer> ints2 = gson.fromJson(json, collectionType);
		 注意我们如何定义集合的类型，不幸的是，没有办法在Java中解决这个问题
	 */

	static class Student{
		private String id;
		@Expose(serialize = false) //不进行序列化和反序列化
		private String name;

		private Date date;

		private double db;

		public Student(String id, String name, Date date, double db) {
			this.id = id;
			this.name = name;
			this.date = date;
			this.db = db;
		}

		public Student(String id, String name, Date date) {
			this.id = id;
			this.name = name;
			this.date = date;
		}

		public Student(String id, String name) {
			this.id = id;
			this.name = name;
		}
		public Student(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public double getDb() {
			return db;
		}

		public void setDb(double db) {
			this.db = db;
		}
	}
}
