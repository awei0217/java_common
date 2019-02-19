package event_bus.task;

/**
 * @author sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱
 */
public interface Task<I,H,O> {

    /**
     * 输入数据
     * @return
     */
    I input(Object o);
    /**
     * 处理数据
     * @param i
     * @return
     */
    H handler(I i);
    /**
     * 输出
     * @param h
     * @return
     */
    O output(H h);
    /**
     * 任务成功回调方法
     * @param t
     */
    void  onSuccess(O t);
    /**
     * 任务异常回调方法
     * @param throwable
     */
    void onFail(Throwable throwable);
    /**
     * 初始化
     */
    void init();


}
