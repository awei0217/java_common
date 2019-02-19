package openhft;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**  Koloboke
 * https://www.cnblogs.com/ydxblog/p/7798775.html
 * @创建人 sunpengwei
 * @创建时间 2019/1/7
 * @描述
 * @联系邮箱
 */
public class ChronicleMapStudy {

    public static void main(String[] args) throws IOException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        ChronicleMap<String, String> map = ChronicleMapBuilder
                .of(String.class, String.class)
                .entries(6000000)
                .createOrRecoverPersistedTo(new File("E://temp.txt"));
        for (int i=5000000;i< 10000000;i++){
            map.put("EMG0123456789"+i,"EMG0123456789"+i);
        }

        System.out.println(map.get("EMG01234567895000000"));
        System.out.println(map.get("EMG01234567896000000"));
        try {
            System.out.println("等待中");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
