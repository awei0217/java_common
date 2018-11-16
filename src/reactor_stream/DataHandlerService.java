package reactor_stream;

import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author sunpengwei
 */
public interface DataHandlerService {


    /**
     * 消费处理结果
     * @param events
     */
    void doNext(List<Object> events);

    /**
     * 发生异常时进入这里
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * 处理完成
     */
    void onComplete();

    /**
     * 在数据元素溢出时对元素的处理方法
     * @param event
     */
    void onEventOverflow(Object event);

    /**
     * 处理数据
     * @param bridge
     * @return
     */
    Flux<Object> operator(Flux<Object> bridge);

}
