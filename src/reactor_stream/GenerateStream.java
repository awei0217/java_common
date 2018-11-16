package reactor_stream;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * @author sunpengwei
 */
public class GenerateStream {

    public Flux<Object> bridge = null;
    public BaseSubscriber<List<Object>> subscriber;

    private final static int pressureBufferSize = 50000;

    public Listener listener = null;
    public static GenerateStream generateStream(DataHandlerService dataHandlerService, Integer maxBufferSize){

        return new GenerateStream(dataHandlerService,maxBufferSize).init();
    }

    public static GenerateStream generateStream(DataHandlerService dataHandlerService, int maxBufferSize,Integer millisecond){

        return new GenerateStream(dataHandlerService,maxBufferSize).init(millisecond);
    }
    GenerateStream(DataHandlerService dataHandlerService, Integer maxBufferSize){
        bridge = Flux.create(sink ->{
            listener = new Listener() {
                @Override
                public void newNext(Object object) {
                    sink.next(object);
                }
                @Override
                public void newComplete() {
                    sink.complete();
                }
            };
        },FluxSink.OverflowStrategy.BUFFER)
                .onBackpressureBuffer(Optional.ofNullable(maxBufferSize).orElse(pressureBufferSize), event -> { dataHandlerService.onEventOverflow(event); },BufferOverflowStrategy.DROP_OLDEST)
                .publishOn(Schedulers.elastic())
                .subscribeOn(Schedulers.parallel())
                .delayElements(Duration.ofSeconds(1)) ;
        subscriber = new BaseSubscriber<List<Object>>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) { // 订阅时首先向上游请求一个元素
                request(1);
            }

            @Override
            protected void hookOnNext(List<Object> events) {
                dataHandlerService.doNext(events);
                //每次处理完一个元素后再请求一个
                request(1);
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                dataHandlerService.onError(throwable);
            }

            @Override
            protected void hookOnComplete() {
                dataHandlerService.onComplete();
            }
        };
        bridge = dataHandlerService.operator(bridge);
    }

    public void accept(Object object){
        listener.newNext(object);
    }

    public void complete(){
        listener.newComplete();
    }

    interface Listener {
        /**
         * 生产一个元素
         * @param object
         */
        void newNext(Object object);

        /**
         * 生产一个完成标志
         */
        void newComplete();
    }

    /**
     * @param millisecond 从缓冲区创建到缓冲区关闭和发射的持续时间。
     * @return
     */
    public GenerateStream init(Integer millisecond){
        bridge.buffer(Duration.ofMillis(Optional.ofNullable(millisecond).orElse(10))).subscribe(subscriber);
        return this;
    }
    public GenerateStream init(){
        bridge.buffer(Duration.ofMillis(10)).subscribe(subscriber);
        return this;
    }

}
