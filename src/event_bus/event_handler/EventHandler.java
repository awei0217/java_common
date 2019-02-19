package event_bus.event_handler;

import event_bus.event.Event;
import event_bus.task.AbstractTask;
import event_bus.task.Task;

/**
 * @author sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱
 */
public interface EventHandler{

    /**
     * 事件处理方法
     * @param event
     * @param task
     */
    void handler(Event event,AbstractTask task);


}
