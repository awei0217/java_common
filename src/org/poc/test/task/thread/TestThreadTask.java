package org.poc.test.task.thread;

import org.junit.Test;
import org.poc.task.Task;
import org.poc.task.TaskEvent;
import org.poc.test.task.thread.handler.ThreadAcceptableHandler;
import org.poc.test.task.thread.handler.ThreadAcceptableHandler1;
import org.poc.test.task.thread.handler.ThreadWorkingHandler;
import org.poc.test.task.thread.handler.ThreadWorkingHandler1;

public class TestThreadTask {

    @Test
    public void tsThreadTask() {

        Task task = Task.generate();

        task.getEventBus().unregister(TaskEvent.Acceptable.class);
        task.getEventBus().register(TaskEvent.Acceptable.class, new ThreadAcceptableHandler());

        task.getEventBus().unregister(TaskEvent.Working.class);
        task.getEventBus().register(TaskEvent.Working.class, new ThreadWorkingHandler());




        int n = 100;
        task.start(100);
        task.start(101);
        task.start(102);
        task.start(103);



       /* for (int i = 1; i < 5; i++) {
            task.start(n + 1);
        }

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < 20; i++) {
            task.start(n + i);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        System.out.println("--------------------------end......................");

    }

}
