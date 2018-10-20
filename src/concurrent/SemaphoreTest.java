package concurrent;

import java.util.concurrent.*;

/**
 * 并发编程许可证学习
 * Created by sunpengwei on 2016/10/4.
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		//设置核心池大小
		int corePoolSize = 5;

		//设置线程池最大能接受多少线程
		int maximumPoolSize  = 100;
		//当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
		long keepActiveTime = 200;

		//设置时间单位，秒
		TimeUnit timeUnit = TimeUnit.SECONDS;

		//设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小为5
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);

		//创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数
		ExecutorService exec = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,workQueue);
		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(5);
		// 模拟20个客户端访问
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semp.release();
						System.out.println("-----------------"+semp.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();
	}
}
