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
public class InputEventHandler<I> implements EventHandler  {

    public static final Object o = new Object();

    @Override
    public void handler(Event event, AbstractTask task) {
        if(event.getEventType().equals(EventType.INPUT_EVENT)){
            I i = (I) task.input();
            while (true){
                try {
                    i= (I) task.input();
                }catch (Exception e){
                    task.onFail(e);
                    continue;
                }
                if (i == null){
                    break;
                }
                try {
                    task.getInputQueue().put(i);
                } catch (InterruptedException e) {
                    //
                }
            }
            try {
                task.getInputQueue().put(o);
            } catch (InterruptedException e) {

            }
        }
    }
}
