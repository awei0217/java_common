package nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author sunpengwei
 * @创建时间 2018/10/20
 * @联系邮箱
 * 文件通道学习测试
 */
public class FileChannelTest {

    public static void main(String[] args) {
        try {
            /**
             * RandomAccessFile支持跳到文件任意位置读写数据，RandomAccessFile对象包含一个记录指针，
             * 用以标识当前读写处的位置，当程序创建一个新的RandomAccessFile对象时，
             * 该对象的文件记录指针对于文件头（也就是0处），
             * 当读写n个字节后，文件记录指针将会向后移动n个字节。除此之外，RandomAccessFile可以自由移动该记录指针
             */
            RandomAccessFile readRandomAccessFile = new RandomAccessFile("E://1.txt","rw");
            RandomAccessFile writeRandomAccessFile = new RandomAccessFile("E://2.txt","rw");

            FileChannel fileChannel1 = readRandomAccessFile.getChannel();
            FileChannel fileChannel2 = writeRandomAccessFile.getChannel();
            /**
             * 申请一段缓冲区
             */
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int bytesRead = fileChannel1.read(byteBuffer);
            //将读出的数据写入文件中
            while (bytesRead != -1){
                // 重置position
                byteBuffer.flip();
                // 这块循环的原因是write 方法可能不会一次将缓冲区的数据全部写入文件。因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
                while (byteBuffer.hasRemaining()){ //判断缓冲区是否有数据
                    fileChannel2.write(byteBuffer);
                }
                // 清楚buffer，也是重置postion  limit
                byteBuffer.clear();
                bytesRead = fileChannel1.read(byteBuffer);
            }
            fileChannel1.close();
            fileChannel2.close();
            // 从指定位置开始读取数据
            fileChannel1.position(100);
            // 返回该实例关联的文件的大小
            fileChannel1.size();
            //可以使用FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除。如：这个例子截取文件的前1024个字节。
            fileChannel1.truncate(1024);
            /**
             FileChannel的force方法
             FileChannel.force()方法将通道里尚未写入磁盘的数据强制写到磁盘上。出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。要保证这一点，需要调用force()方法。
             force()方法有一个boolean类型的参数，指明是否同时将文件元数据（权限信息等）写到磁盘上。
             下面的例子同时将文件数据和元数据强制写到磁盘上：
             */
             fileChannel2.force(true);

            // 通道数据传输
            //将fileChannel1通道中的数据传输到fileChannel2通道中
            fileChannel1.transferTo(0,fileChannel1.size(),fileChannel2);
            //将fileChannel2通道中的数据传输到fileChannel1通道中
            fileChannel1.transferFrom(fileChannel2,0,fileChannel2.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
