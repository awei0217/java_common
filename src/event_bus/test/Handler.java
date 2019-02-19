package event_bus.test;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/24
 * @描述
 * @联系邮箱
 */
public class Handler {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Handler(String name){
        this.name = name;
    }
}
