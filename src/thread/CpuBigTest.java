package thread;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/25
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */

import java.io.File;

/**
 * 此程序会会占用过高cpu,
 * 用来模拟消耗CPU，进行排查
 */
public class CpuBigTest {
    public static void main(String[] args) {

        //启动一个线程
        new Thread( ()-> {
            int num = 0;
            long start = System.currentTimeMillis() / 1000;
            //模拟占用大量cpu
            while (true) {
                num = num + 1;
                if (num == Integer.MAX_VALUE){
                    System.out.println("reset");
                    num = 0;
                }
                if ((System.currentTimeMillis() / 1000) - start > 1000) {
                    return;
                }
            }

        }).start();

    }
}

