package atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by sunpengwei on 2016/10/4.
 */
public class AtomicIntegerArrayTest2 {
	private static int threadCount=10;
	private static CountDownLatch countDown=new CountDownLatch(threadCount);
	static int[] values=new int[10];
	private final static AtomicIntegerArray ATOMIC_INTEGER_ARRAY = new AtomicIntegerArray(values);
	private static class Counter implements Runnable{

		public void run() {
			for(int i=0;i<100;i++){
				for(int j=0;j<10;j++){//所有元素+1
					//ATOMIC_INTEGER_ARRAY.addAndGet(j,j++);
					values[j]++;
				}
				//System.out.println(i);
			}
			countDown.countDown();
		}
	}
	public static void main(String[] args) throws InterruptedException{
		System.out.println(countDown.getCount());
		Thread[] threads=new Thread[threadCount];
		for(int i=0;i<threadCount;i++){
			threads[i]=new Thread(new Counter());
		}
		for(int i=0;i<threadCount;i++){
			threads[i].start();
		}
		countDown.await();
		System.out.println(countDown.getCount());
		for(int i=0;i<10;i++){
			System.out.print(values[i]+" ");
			//System.out.println(ATOMIC_INTEGER_ARRAY.get(i));
		}
	}
}
