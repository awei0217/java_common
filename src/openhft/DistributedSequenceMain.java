package openhft;


import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;
/**
 * @创建人 sunpengwei
 * @创建时间 2019/1/8
 * @描述
 * @联系邮箱
 */
public class DistributedSequenceMain {

    public static void main(String... ignored) throws IOException {
        File file = new File("E:\\sequence-numbers.dat");
        try (ChronicleMap<String, String> map =
                     ChronicleMapBuilder.of(String.class, String.class)
                             .entries(6000000)
                             .actualSegments(1)
                             .averageKeySize(1)
                             .averageValueSize(100)
                             .createOrRecoverPersistedTo(file)) {
            // throughput test.
            for (int t = 0; t < 5000000; t++) {
                map.put("EBU"+t,"EBU"+t);
            }
            long start = System.currentTimeMillis();
            System.out.println(map.get("EBU400000"));
            System.out.println(map.put("EBU400000","eqweww"));
            System.out.println(map.get("EBU400000"));
            System.out.println((System.currentTimeMillis()-start));
        }
    }
}
