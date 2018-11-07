package event_bus.test;

import event_bus.event.Event;
import event_bus.task.AbstractTask;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/24
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class TestTask extends AbstractTask<Input,Handler,Output> {

    private AtomicInteger input = new AtomicInteger(0);
    private AtomicInteger success = new AtomicInteger(0);
    @Override
    public Input input() {
        if (input.incrementAndGet() < 1000){
            try {
                //模拟查询数据 20 毫秒
                Thread.sleep(20);
            } catch (InterruptedException e) {

            }
            return new Input("输入数据..............................");

        }
        return null;
    }

    @Override
    public Handler handler(Input input) {
        try {
            //模拟处理数据 20 毫秒
            Thread.sleep(20);
        } catch (InterruptedException e) {

        }
        return new Handler("处理数据..............................");
    }

    @Override
    public Output output(Handler handler) {
        try {
            //模拟处理数据 20 毫秒
            Thread.sleep(20);
        } catch (InterruptedException e) {

        }
        return new Output("输出数据..............................");
    }

    @Override
    public void onSuccess(Output t) {
        System.out.println("处理成功"+success.incrementAndGet());
    }

    @Override
    public void onFail(Throwable throwable) {

    }
    private TestTask(LinkedList<Event> events, BlockingQueue<Input> inputQueue, BlockingQueue<Handler> handlerQueue){
        setEventLinkedList(events);
        setInputQueue(inputQueue);
        setHandlerQueue(handlerQueue);
    }
    public static AbstractTask<Input, Handler, Output> buildTask(LinkedList<Event> events, BlockingQueue<Input> inputQueue, BlockingQueue<Handler> handlerQueue){

        return new TestTask(events,inputQueue,handlerQueue);
    }


}
