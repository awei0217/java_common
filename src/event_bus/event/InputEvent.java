package event_bus.event;

/**
 * @author sunpengwei
 * @创建时间 2018/10/23
 * @描述 初始化事件
 * @联系邮箱 sunpengwei@jd.com
 */
public class InputEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return EventType.INPUT_EVENT;
    }

}
