package thread;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/25
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */

/**
 * 模拟死锁
 */
/** jstack 后面跟的参数
 * -l 长列表. 打印关于锁的附加信息,例如属于java.util.concurrent的ownable synchronizers列表，会使得JVM停顿得长久得多（可能会差很多倍，比如普通的jstack可能几毫秒和一次GC没区别，加了-l 就是近一秒的时间），-l 建议不要用。一般情况不需要使用
 * -m 打印java和native c/c++框架的所有栈信息.可以打印JVM的堆栈,显示上Native的栈帧，一般应用排查不需要使用
 */
// 正常情况下 jstack pid 就可以了
/**
 * 死锁排查  jstack -l pid  如下是查找出来的信息
 * Found one Java-level deadlock:
 =============================
 "Thread-1":
 waiting to lock monitor 0x000000000d268878 (object 0x0000000770288ae8, a java.lang.Class),
 which is held by "Thread-0"
 "Thread-0":
 waiting to lock monitor 0x000000000d269d18 (object 0x0000000770288b50, a java.lang.Class),
 which is held by "Thread-1"

 Java stack information for the threads listed above:
 ===================================================
 "Thread-1":
 at thread.DeadLockTest$ThreadName1.run(DeadLockTest.java:48)
 - waiting to lock <0x0000000770288ae8> (a java.lang.Class for thread.DeadLockTest$A)
 - locked <0x0000000770288b50> (a java.lang.Class for thread.DeadLockTest$B)
 at java.lang.Thread.run(Thread.java:745)
 "Thread-0":
 at thread.DeadLockTest$ThreadName0.run(DeadLockTest.java:35)
 - waiting to lock <0x0000000770288b50> (a java.lang.Class for thread.DeadLockTest$B)
 - locked <0x0000000770288ae8> (a java.lang.Class for thread.DeadLockTest$A)
 at java.lang.Thread.run(Thread.java:745)

 Found 1 deadlock.
 */
//jstack用于生成java虚拟机当前时刻的线程快照。线程快照是当前java虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等

/**
 jstack 打印出来的线程状态的解释
 RUNNABLE,在虚拟机内执行的。运行中状态，可能里面还能看到locked字样，表明它获得了某把锁。
 BLOCKED,受阻塞并等待监视器锁。被某个锁(synchronizers)給block住了。
 WATING,无限期等待另一个线程执行特定操作。等待某个condition或monitor发生，一般停留在park(), wait(), sleep(),join() 等语句里。
 TIMED_WATING,有时限的等待另一个线程的特定操作。和WAITING的区别是wait() 等语句加上了时间限制 wait(timeout)。
 TERMINATED,已退出的。
 */
public class DeadLockTest {


    //模拟资源1
    class A{

    }
    //模拟资源2
    class B{

    }

    static class ThreadName0 implements Runnable{

        @Override
        public void run() {
            synchronized (A.class){ //线程0拿到资源1
                try {
                    System.out.println(Thread.currentThread().getName()+"拿到A了");
                    Thread.sleep(10); //等待线程1获取资源2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B.class){ //线程0等待获取资源2 ，但因为线程1已经获取资源2等待获取资源1 所以发生死锁
                    System.out.println(Thread.currentThread().getName()+"拿到B了");
                }
            }
        }
    }

    static class ThreadName1 implements Runnable{

        @Override
        public void run() {
            synchronized (B.class){// 线程1拿到资源2
                System.out.println(Thread.currentThread().getName()+"拿到B了");
                synchronized (A.class){ //线程1等待获取资源1 ，但因为线程0已经获取资源1等待获取资源2 所以发生死锁
                    System.out.println(Thread.currentThread().getName()+"拿到A了");
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadName0 threadName0 =new ThreadName0();
        ThreadName1 threadName1 =new ThreadName1();
        new Thread(threadName0).start();
        new Thread(threadName1).start();

    }


}
