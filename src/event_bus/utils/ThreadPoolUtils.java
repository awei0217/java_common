package event_bus.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *@Author sunpengwei
 * 线程池工具类
 */
public final class ThreadPoolUtils {
	/**
	 * 创建一个线程池
     */
	static class SingletonHolder {

		/**
		 * 核心线程池大小
         */
		final static int corePoolSize = 4 * 2  +1;
		/**
		 * 最大线程池大小
         */
		final static int maximumPoolSize =  corePoolSize * 2;
		/**
		 * 空闲线程保持多长存活时间
         */
		final static long keepAliveTime = 60;

		final static int queueSize = 100;

		final static BlockingQueue<Runnable> blockQueue = new ArrayBlockingQueue<Runnable>(queueSize);

		/**
		 * 创建一个线程池
         */
		final static ThreadPoolExecutor defExecutor =
				new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,blockQueue,new MyThreadFactory("event-bus-thread-group"));

		SingletonHolder() {
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("销毁线程池:"+defExecutor.getClass().getName());
					defExecutor.shutdown();
				}
			}));
		}
	}

	static final ExecutorService getExecutor() {
		return SingletonHolder.defExecutor;
	}

	static class MyThreadFactory implements ThreadFactory{
		/**
		 * 线程编号
         */
		private AtomicInteger atomicInteger = new AtomicInteger(1);
		/**
		 * 线程池的名字
         */
		private String threadPoolName;
		/**
		 * 线程池中的线程所在的分组
         */
		private ThreadGroup threadGroup;

		MyThreadFactory(String threadPoolName){
			this.threadPoolName = threadPoolName;
			this.threadGroup = new ThreadGroup(threadPoolName);
			this.threadPoolName = threadPoolName;
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(threadGroup,r,"-"+atomicInteger.getAndIncrement());
			return thread;
		}
	}

	public static ThreadPoolExecutor getDefaultThreadPool(){

		return SingletonHolder.defExecutor;
	}

}
