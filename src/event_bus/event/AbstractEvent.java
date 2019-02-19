package event_bus.event;

import java.util.Date;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/23
 * @描述 抽象时间类
 * @联系邮箱
 */
public abstract class AbstractEvent implements Event {


    private Date eventStartDate;

    private Date eventEndDate;

    @Override
    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    @Override
    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
}
