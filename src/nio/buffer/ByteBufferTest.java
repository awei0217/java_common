package nio.buffer;

import java.nio.ByteBuffer;

/**
 * @Author sunpengwei
 * @创建时间 2018/10/20
 * @描述
 * @联系邮箱
 * 缓冲区学习测试
 */
public class ByteBufferTest {

    public static void main(String[] args) {

        // 创建一个buffer缓冲区 （本质上创建了一个数组） HeapByteBuffer
        // ByteBuffer 是一个抽象类

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 往缓冲区写入数据 ，相当于数组下标往后移动
        // 往buffer写数据有两种方式，一种调用put方法，一种是调用调用的channel的read方法（把通道数据读取到buffer）
        byteBuffer.put((byte) 1);
        // 将byteBuffer 从写模式切换到读模式 ，也就是将position置0,limit设置为之前写数据后position的值
        byteBuffer.flip();

        // buffer 的读有两种方式,一种调用get方法，一种是调用channel的write方法（从buffer读数据到通道）
        byte b = byteBuffer.get();

        // 这个方法将position 设置为0，limit不变，允许重新读取
        byteBuffer.rewind();
        //清空buffer 缓冲区，position将被设回0，limit被设置成 capacity的值
        //换句话说，Buffer 被清空了。Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
        //如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
        byteBuffer.clear();
        /**
         * compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
         * limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据
         */
        byteBuffer.compact();

        /**
         * mark()与reset()方法
         * 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用Buffer.reset()方法恢复到这个
         */
        byteBuffer.mark();
        byteBuffer.reset();

        /**
         * 判断缓冲区是否有元素，true 有 false 没有
         */
        byteBuffer.hasRemaining();



    }
}
