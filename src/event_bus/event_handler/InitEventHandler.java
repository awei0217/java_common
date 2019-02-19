package event_bus.event_handler;

import event_bus.event.Event;
import event_bus.event.EventType;
import event_bus.task.AbstractTask;
import event_bus.task.Task;

/**
 * @author sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱
 */
public class InitEventHandler implements EventHandler {


    @Override
    public void handler(Event event, AbstractTask task) {
        if (event.getEventType().equals(EventType.INIT_EVENT)){
            try {
                task.init();
            }catch (Exception e){
                task.onFail(e);
            }
        }
    }
}
