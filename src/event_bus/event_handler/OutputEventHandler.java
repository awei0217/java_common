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
public class OutputEventHandler<H,O> implements EventHandler  {
    @Override
    public void handler(Event event, AbstractTask task) {
        if(event.getEventType().equals(EventType.OUTPUT_EVENT)){
            H h = (H) task.getHandlerQueue().poll();
            while (h != HandlerEventHandler.o){
                O o = null;
                try{
                    o = (O) task.output(h);
                }catch (Exception e){
                    task.onFail(e);
                    try {
                        h = (H) task.getHandlerQueue().take();
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                task.onSuccess(o);
                try {
                    h = (H) task.getHandlerQueue().take();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
