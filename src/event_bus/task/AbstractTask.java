package event_bus.task;

import event_bus.event.Event;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

/**
 * @Author sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱
 */
public abstract class AbstractTask<I,H,O> implements Task<I,H,O> {

    /**
     * 存放事件的队列
     */
    private LinkedList<Event> eventLinkedList;
    /**
     * 输入数据的缓存队列
     */
    private BlockingQueue<I> inputQueue;
    /**
     * 处理数据的缓存队列
     */
    private BlockingQueue<H> handlerQueue;

    /**
     * input方法入参
     */
    private Object object;



    public LinkedList<Event> getEventLinkedList() {
        return eventLinkedList;
    }

    public void setEventLinkedList(LinkedList<Event> eventLinkedList) {
        this.eventLinkedList = eventLinkedList;
    }
    /**
     * 初始化
     */
    @Override
    public void init(){

    }

    public BlockingQueue<I> getInputQueue() {
        return inputQueue;
    }

    public void setInputQueue(BlockingQueue<I> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public BlockingQueue<H> getHandlerQueue() {
        return handlerQueue;
    }

    public void setHandlerQueue(BlockingQueue<H> handlerQueue) {
        this.handlerQueue = handlerQueue;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
