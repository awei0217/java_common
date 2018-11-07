package nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author sunpengwei
 * @创建时间 2018/10/27
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */

public class PipeTest {

    /**
     * Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
     */
    public static void main(String[] args) {

        try {
            //打开一个管道
            Pipe pipe = Pipe.open();
            //向管道写数据
            new Thread(() ->{
                Pipe.SinkChannel sinkChannel = pipe.sink();
                System.out.println(sinkChannel.isBlocking());
                System.out.println(sinkChannel.blockingLock());
                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                writeBuffer.put(new String("数据").getBytes());
                writeBuffer.flip();
                while (true){
                    while (writeBuffer.hasRemaining()){
                        //把buffer中的数据写入管道
                        try {
                            sinkChannel.write(writeBuffer);
                        } catch (IOException e) {
                        }
                    }
                    writeBuffer.clear();
                    writeBuffer.put(new String("数据").getBytes());
                    writeBuffer.flip();
                }


            }).start();
            new Thread(()->{
                //从管道读数据
                Pipe.SourceChannel sourceChannel = pipe.source();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                try {
                    int num = sourceChannel.read(readBuffer);
                    while (num>0){
                        readBuffer.flip();
                        while (readBuffer.hasRemaining()){
                            byte [] bs = readBuffer.array();
                            System.out.println(new String(bs).toString());
                        }
                        readBuffer.clear();
                        num = sourceChannel.read(readBuffer);
                    }
                } catch (IOException e) {

                }
            }).start();
        } catch (IOException e) {

        }
    }
}
