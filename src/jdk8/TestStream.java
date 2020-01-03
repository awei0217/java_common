package jdk8;


import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sunpengwei on 2016/10/21.
 */

public class TestStream {
	private enum Status {
		OPEN, CLOSED
	};
	public static void main(String[] args) {


		List<String> stringList = Lists.newArrayList();
		List<String> stringList1 = Lists.newArrayList(null,null);
		stringList.addAll(stringList1);

		stringList = stringList.stream().filter(s -> s!=null).collect(Collectors.toList());

		stringList.stream().collect(Collectors.toMap(s -> {
			return s.toCharArray();
		},s ->{return s;}));

		System.out.println(stringList.size());
		List<String> finalStringList = stringList;
		List<List<String>> splitList = Stream.iterate(0, n -> n + 1).limit(stringList.size()).parallel().map(a -> finalStringList.stream().skip(a * 2).limit(2).parallel().collect(Collectors.toList())).filter(l-> l.size()!=0).collect(Collectors.toList());




		List<List<String>> mglist = new ArrayList<>();
		List<String> finalStringList1 = stringList;
		Stream.iterate(0, n -> n + 1).limit(stringList.size()).forEach(i -> {
			mglist.add(finalStringList1.stream().skip(i * 2).limit(2).collect(Collectors.toList()));
		});

		System.out.println(mglist);
		final Collection< Task > tasks = Arrays.asList(
				new Task( Status.OPEN, 5 ),
				new Task( Status.OPEN, 13 ),
				new Task( Status.CLOSED, 8 )
		);
		long s = tasks.stream().filter(task -> task.getStatus()==Status.CLOSED).mapToInt(Task::getPoints).sum();
		System.out.println(s);

		List<String> list = Arrays.asList("hello","I","love","you");
		List<String> distincted = list.stream()
				.skip(2)
				.collect(Collectors.toList());

		final double totalPoints = tasks
				.stream()
				.parallel()
				.map( task -> task.getPoints() ) // or map( Task::getPoints )
				.reduce( 0,Integer::sum );

		System.out.println( "Total points (all tasks): " + totalPoints );
		//经常会有这个一个需求：我们需要按照某种准则来对集合中的元素进行分组。Stream也可以处理这样的需求，下面是一个例子：
		Map<Status,List<Task>> map = tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
		System.out.println(map);

		//让我们来计算整个集合中每个task分数（或权重）的平均值来结束task的例子。
		// Calculate the weight of each tasks (as percent of total points)
		final Collection< String > result = tasks
				.stream()                                        // Stream< String >
				.mapToInt( Task::getPoints )                     // IntStream
				.asLongStream()                                  // LongStream
				.mapToDouble( points -> points / totalPoints )   // DoubleStream
				.boxed()                                         // Stream< Double >
				.mapToLong( weigth -> ( long )( weigth * 100 ) ) // LongStream
				.mapToObj( percentage -> percentage + "%" )      // Stream< String>
				.collect( Collectors.toList() );                 // List< String >

		System.out.println( result );

		Map<String, List<String>> map1 = tasks.stream().collect(Collectors.toMap(
				t -> t.toString()
				, t ->{
					List<String> pointList = new ArrayList<>();
					pointList.add(String.valueOf(t.getPoints()));
					return pointList;
				},
				//// 重复时将现在的值全部加入到之前的值内
				(List<String> value1, List<String> value2) -> {
					value1.addAll(value2);
					return value1;
				}));

		List<String> list11 = Arrays.asList("hello","hello","hello","hello");

		list11.stream().filter( s2 -> {return ("hello").equals(s2);}).findFirst();

		boolean falg = list.stream().anyMatch(s2 -> s2.equals("hello"));
	}



	private static final class Task {
		private final Status status;
		private final Integer points;

		Task( final Status status, final Integer points ) {
			this.status = status;
			this.points = points;
		}

		public Integer getPoints() {
			return points;
		}

		public Status getStatus() {
			return status;
		}

		@Override
		public String toString() {
			return String.format( "[%s, %d]", status, points );
		}
	}
}

