package cache;

import org.caffinitas.ohc.CacheSerializer;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

import java.nio.ByteBuffer;

/**
 * @创建人 sunpengwei
 * @创建时间 2019/1/9
 * @描述
 * @联系邮箱
 */
public class OhcCacheTest {

    public static void main(String[] args) {

        OHCache<String, String> ohCache = OHCacheBuilder.<String, String>newBuilder()
                .keySerializer(new MySerializer())
                .valueSerializer(new MySerializer())
                .build();

        for (int i=1;i< 50000;i++){
            ohCache.put("EMG0123456789"+i,"EMG0123456789"+i);
        }

        System.out.println(ohCache.size());
        long start = System.nanoTime();
        System.out.println(ohCache.get("EMG0123456789"+30000));
        System.out.println(System.nanoTime()-start);

    }


}

class MySerializer implements CacheSerializer<String> {
    @Override
    public void serialize(String s, ByteBuffer byteBuffer) {
        byteBuffer.putInt(s.getBytes().length);
        byteBuffer.put(s.getBytes());
    }
    @Override
    public String deserialize(ByteBuffer byteBuffer) {
        byte[] arr = new byte[byteBuffer.getInt()];
        byteBuffer.get(arr);
        return new String(arr);
    }

    @Override
    public int serializedSize(String s) {
        return 4 + s.getBytes().length;
    }
}
