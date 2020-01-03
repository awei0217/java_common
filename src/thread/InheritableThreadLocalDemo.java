package thread;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2019/9/16 14:13
 */
public class InheritableThreadLocalDemo {

    //父子线程传值，InheritableThreadLocal线程不安全的
    static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();


    public static void main(String[] args) throws InterruptedException {
        InheritableThreadLocalDemo.threadLocal.set("superWorld");
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Service().call();
            }
        }).start();

        String  s  = threadLocal.get();

        System.out.println("main:" + Thread.currentThread().getName() + " : " + InheritableThreadLocalDemo.threadLocal.get());
        Thread.sleep(1000 * 10);
    }
}


    class Service {
        public void call() {
            System.out.println("Service:" + Thread.currentThread().getName() + " : " + InheritableThreadLocalDemo.threadLocal.get());
            //new Dao().call();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Dao().call();
                }
            }).start();
        }
    }

    class Dao {
        public void call() {
            System.out.println("Dao:" + Thread.currentThread().getName() + " : " + InheritableThreadLocalDemo.threadLocal.get());
        }
    }
