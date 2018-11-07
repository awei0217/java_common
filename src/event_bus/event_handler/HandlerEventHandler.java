package event_bus.event_handler;

import event_bus.event.Event;
import event_bus.event.EventType;
import event_bus.task.AbstractTask;
import event_bus.task.Task;

/**
 * @author sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class HandlerEventHandler<H,I> implements EventHandler  {

    public static final Object o = new Object();

    @Override
    public void handler(Event event, AbstractTask task) {
        if (event.getEventType().equals(EventType.HANDLER_EVENT)){
            I i = (I) task.getInputQueue().poll();
            while (i != InputEventHandler.o){
                H h = (H) task.handler(i);
                try {
                    task.getHandlerQueue().put(h);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    i= (I) task.getInputQueue().take();
                } catch (InterruptedException e) {

                }
            }
            try {
                task.getHandlerQueue().put(o);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
