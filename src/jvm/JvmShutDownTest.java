package jvm;


import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
public class JvmShutDownTest{

    static {
        /**
         * 打印JVM退出堆栈信息
         */
        jvmExitHook();
    }

    public static void main(String[] args) {
    }

    public static void jvmExitHook() {
        System.out.println("注册JVM Shutdown钩子方法---------");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
                System.out.println("####################内存信息####################");
                System.out.println("Heap Memory: " + memorymbean.getHeapMemoryUsage());
                System.out.println("Non Heap Memory: " + memorymbean.getNonHeapMemoryUsage());

                List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
                if (list != null && list.size() > 0) {
                    System.out.println("####################Gc信息####################");
                    for (GarbageCollectorMXBean gcBean : list) {
                        String s = "gc name=" + gcBean.getName() + ",gc count=" + gcBean.getCollectionCount() + ",gc time=" + gcBean.getCollectionTime();
                        System.out.println(s);
                    }
                }

                ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                long[] ids = threadBean.getAllThreadIds();
                System.out.println("####################线程信息####################");
                for (long id : ids) {
                    ThreadInfo threadInfo = threadBean.getThreadInfo(id, Integer.MAX_VALUE);
                    if (threadInfo != null) {
                        String s = "blockcount=" + threadInfo.getBlockedCount() + ",blocktime=" + threadInfo.getBlockedTime();
                        s = s + ",waitedcount=" + threadInfo.getWaitedCount() + ",waitedtime=" + threadInfo.getWaitedTime();
                        System.out.println(s);
                        System.out.println(getThreadInfo(threadInfo));
                    }
                }
                long[] deadlock_ids = threadBean.findDeadlockedThreads();
                if (deadlock_ids != null) {
                    System.out.println("####################死锁信息####################");
                    for (long id : deadlock_ids) {
                        System.out.println("死锁的线程号：" + id);
                    }
                }
            }

        });
    }

    public static String getThreadInfo(ThreadInfo t) {

        try {
            StringBuilder sb = new StringBuilder("\"" + t.getThreadName() + "\"" + " Id=" + t.getThreadId() + " " + t.getThreadState());
            if (t.getLockName() != null) {
                sb.append(" on " + t.getLockName());
            }
            if (t.getLockOwnerName() != null) {
                sb.append(" owned by \"" + t.getLockOwnerName() + "\" Id=" + t.getLockOwnerId());
            }
            if (t.isSuspended()) {
                sb.append(" (suspended)");
            }
            if (t.isInNative()) {
                sb.append(" (in native)");
            }
            sb.append('\n');
            int i = 0;
            for (StackTraceElement ste : t.getStackTrace()) {
                sb.append("\tat " + ste.toString());
                sb.append('\n');
                if (i == 0 && t.getLockInfo() != null) {
                    Thread.State ts = t.getThreadState();
                    switch (ts) {
                        case BLOCKED:
                            sb.append("\t-  blocked on " + t.getLockInfo());
                            sb.append('\n');
                            break;
                        case WAITING:
                            sb.append("\t-  waiting on " + t.getLockInfo());
                            sb.append('\n');
                            break;
                        case TIMED_WAITING:
                            sb.append("\t-  waiting on " + t.getLockInfo());
                            sb.append('\n');
                            break;
                        default:
                    }
                }

                for (MonitorInfo mi : t.getLockedMonitors()) {
                    if (mi.getLockedStackDepth() == i) {
                        sb.append("\t-  locked " + mi);
                        sb.append('\n');
                    }
                }
            }
            if (i < t.getStackTrace().length) {
                sb.append("\t...");
                sb.append('\n');
            }

            LockInfo[] locks = t.getLockedSynchronizers();
            if (locks.length > 0) {
                sb.append("\n\tNumber of locked synchronizers = " + locks.length);
                sb.append('\n');
                for (LockInfo li : locks) {
                    sb.append("\t- " + li);
                    sb.append('\n');
                }
            }
            sb.append('\n');
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

