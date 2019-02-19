package event_bus.event;

/**
 * @author sunpengwei
 * @创建时间 2018/10/23
 * @描述 初始化事件
 * @联系邮箱
 */
public class OuputEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return EventType.OUTPUT_EVENT;
    }

}
