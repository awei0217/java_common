package nio.scatter_gather;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/21
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Gather 聚集，将多个buffer中的数据读到一个通道中
 */
public class GatherTest {

    public static void main(String[] args) {

        // 比如讲消息头 和消息体写入一个通道中

        ByteBuffer head = ByteBuffer.allocate(10);
        head.put((byte) 1);
        head.flip();
        ByteBuffer body = ByteBuffer.allocate(20);
        body.put((byte) 2);
        body.flip();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("E://2.txt"));
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer [] array = {head,body};
            //将多个buffer 中数据写入一个通道中。write()方法会按照buffer在数组中的顺序，将数据写入到channel
            //注意只有position和limit之间的数据才会被写入。因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，
            // 那么这58byte的数据将被写入到channel中。因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息
            fileChannel.write(array);
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
