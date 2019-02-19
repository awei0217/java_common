package event_bus;

import event_bus.event.Event;
import event_bus.event.EventType;
import event_bus.event_handler.*;
import event_bus.task.AbstractTask;
import event_bus.utils.ThreadPoolUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sunpengwei
 * @创建人 sunpengwei
 * @创建时间 2018/10/23
 * @描述
 * @联系邮箱
 */
public class EventBus {



    private static final Map<EventType,EventHandler> eventEventHandlerMapRegister = new HashMap<>();

    private BlockingQueue<AbstractTask> taskQueue= new LinkedBlockingQueue<>();


    static {
        /**
         * 注册事件类型对应的事件处理器
         */
        eventEventHandlerMapRegister.put(EventType.INIT_EVENT, new InitEventHandler());
        eventEventHandlerMapRegister.put(EventType.INPUT_EVENT, new InputEventHandler<>());
        eventEventHandlerMapRegister.put(EventType.HANDLER_EVENT, new HandlerEventHandler<>());
        eventEventHandlerMapRegister.put(EventType.OUTPUT_EVENT, new OutputEventHandler<>());
    }


    private EventBus() {
    }

    public static EventBus buildEventBus() {
        EventBus eventBus  = new EventBus();
        return eventBus;
    }

    public void start() {

        while (true){
            AbstractTask task = null;
            try {
                task = taskQueue.take();
            } catch (InterruptedException e) {

            }
            TaskExecute.submitTask(task);
        }
    }

    public void put(AbstractTask task){
        taskQueue.add(task);
    }

    static class TaskExecute{

        private static ThreadPoolExecutor executorService = ThreadPoolUtils.getDefaultThreadPool();

        private static void submitTask(AbstractTask task){

            List<Event> eventList = task.getEventLinkedList();
            if (eventList == null || eventList.size()==0){
               return;
            }
            //获取线程池最大线程数
            int maxThreadNum = executorService.getMaximumPoolSize();
            while (true){
                //获取当前线程池活动线程数据
                int activeThreadNum = executorService.getActiveCount();
                //线程池线程数已经满了,任务被抛弃
                if (maxThreadNum - activeThreadNum > eventList.size()){
                    for (Event event:eventList){
                        EventHandler eventHandler = eventEventHandlerMapRegister.get(event.getEventType());
                        executorService.execute(new HandlerRunnable(eventHandler,event,task));
                    }
                    break;
                }
                try {
                    // 休眠一秒钟,等待线程池让出空闲队列或者线程
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
        static class HandlerRunnable implements Runnable{
            private EventHandler eventHandler;
            private Event event;
            private AbstractTask task;

            public HandlerRunnable (EventHandler eventHandler,Event event,AbstractTask task){
                this.eventHandler = eventHandler;
                this.event = event;
                this.task =task;
            }
            @Override
            public void run() {
                eventHandler.handler(event,task);
            }
        }
    }
}
