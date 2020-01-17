package queue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.*;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/19
 * @描述
 * @联系邮箱
 */
public class SyncQueueTester {


    private static ExecutorService executor = new ThreadPoolExecutor(1, 1,
            1000, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(true),
            new ThreadPoolExecutor.DiscardPolicy());

//    public static void main(String[] args) throws InterruptedException {
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(Calendar.DAY_OF_MONTH,0);
//        calendar1.set(Calendar.HOUR_OF_DAY,0);
//        calendar1.set(Calendar.SECOND,0);
//        calendar1.set(Calendar.MINUTE,0);
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime()));
//        calendar1.set(Calendar.DAY_OF_MONTH,0);
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime()));
//
//
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        kickOffEntry(1);
//
//
//
//        executor.shutdown();
//    }

    private static void kickOffEntry(int index) {
        executor.submit(
                new Callable<Void>() {
                    @Override
                    public Void call() throws InterruptedException {
                        System.out.println("start " + index);
                        Thread.sleep(1000); // pretend to do work
                        System.out.println("stop " + index);
                        kickOffEntry(2);
                        return null;
                    }
                }
        );
    }







}
