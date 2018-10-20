package concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by sunpengwei on 2017/6/24.
 */
public class CompletionServiceTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/**
		 * 内部维护11个线程的线程池
		 */
		ExecutorService exec = Executors.newFixedThreadPool(11);
		/**
		 * 容量为10的阻塞队列
		 */
		final BlockingQueue queue = new LinkedBlockingQueue(10);
		//实例化CompletionService
		final CompletionService completionService = new ExecutorCompletionService(exec, queue);
		/**
		 * 模拟瞬间产生10个任务，且每个任务执行时间不一致
		 */
		for (int i = 0; i < 10; i++) {
			completionService.submit(new Callable() {
				@Override
				public Integer call() throws Exception {
					int ran = new Random().nextInt(1000);
					Thread.sleep(ran);
					System.out.println(Thread.currentThread().getName() + " 休息了 " + ran);
					return ran;
				}
			});
		}

		/**
		 * 立即输出结果
		 */
		for (int i = 0; i < 10; i++) {
			try {
				//谁最先执行完成，直接返回
				Future f = completionService.take();
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		exec.shutdown();
	}

}


