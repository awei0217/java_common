package nio.scatter_gather;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/21
 * @描述
 * @联系邮箱
 */

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
 * 因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中
 */
public class ScatterTest {

    public static void main(String[] args) {
        // 比如有时需要将消息头 和消息体分开分开处理

        ByteBuffer head = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(20);

        ByteBuffer [] array = {head,body};

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("E://1.txt"));
            fileInputStream.read();
            FileChannel  fileChannel = fileInputStream.getChannel();
            /**
             * 注意buffer首先被插入到数组，然后再将数组作为channel.read() 的输入参数。
             * read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，
             * 当一个buffer被写满后，channel紧接着向另一个buffer中写。
             * Scattering Reads在移动下一个buffer前，必须填满当前的buffer，
             * 这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。
             */
            fileChannel.read(array);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
