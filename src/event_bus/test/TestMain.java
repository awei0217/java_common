package event_bus.test;

import event_bus.EventBus;
import event_bus.event.*;
import event_bus.task.AbstractTask;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/24
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class TestMain {

    public static void main(String[] args) {

        EventBus eventBus = EventBus.buildEventBus();
        LinkedList<Event> events = new LinkedList<>();
        events.add(new InitEvent());
        events.add(new InputEvent());
        events.add(new HandlerEvent());
        events.add(new OuputEvent());

        BlockingQueue<Input> inputBlockingQueue = new ArrayBlockingQueue<Input>(100);
        BlockingQueue<Handler> handlerBlockQueue = new ArrayBlockingQueue<Handler>(100);
        AbstractTask task = TestTask.buildTask(events,inputBlockingQueue,handlerBlockQueue);
        eventBus.put(task);
        eventBus.start();
    }
}
