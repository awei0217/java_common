package atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sunpengwei on 2016/10/4.
 */
public class SafeAddWithAtomicInteger {

	private static int threadCount=10;
	private static CountDownLatch countDown=new CountDownLatch(threadCount);
	private static AtomicInteger count=new AtomicInteger(0);//原子操作类
	private static class Counter implements Runnable{
		@Override
		public void run() {
			for(int i=0;i<1000;i++){
				count.addAndGet(1);
			}
			countDown.countDown();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] threads=new Thread[threadCount];
		for(int i=0;i<threadCount;i++){
			threads[i]=new Thread(new Counter());
		}
		for(int i=0;i<threadCount;i++){
			threads[i].start();;
		}
		countDown.await();
		System.out.println(count.get());
	}
}
