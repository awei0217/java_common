package event_bus.event;

import java.util.Date;
/**
 * @Author sunpengwei
 * @创建时间 2018/10/23
 * @描述 事件类
 * @联系邮箱
 */
public interface Event {
    /**
     * 获取事件类型
     * @return
     */
    EventType getEventType();
    /**
     * 获取事件开始时间
     * @return
     */
    Date getEventStartDate();
    /**
     * 获取事件结束时间
     * @return
     */
    Date getEventEndDate();

}
