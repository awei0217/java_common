package atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by sunpengwei on 2016/10/4.
 */
public class AtomicIntegerArrayTest3 {
	private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{9});
	public static void main(String[] args) {
		atomicIntegerArray.addAndGet(0,10);

		System.out.println(atomicIntegerArray.get(0));
	}
}
