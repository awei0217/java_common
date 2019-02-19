package event_bus.event;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱
 */
public enum EventType {


    INIT_EVENT(10,"初始化"),
    INPUT_EVENT(20,"输入"),
    HANDLER_EVENT(30,"处理"),
    OUTPUT_EVENT(40,"输出");

    /**
     * 编号
     */
    private Integer code;
    /**
     * 名称
     */
    private String name;

    EventType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
