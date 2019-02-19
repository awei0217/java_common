package org.poc.test.task.stream;

import org.poc.task.Task;
import org.poc.task.TaskEvent;
import org.poc.test.task.stream.handler.StreamAcceptableHandler;
import org.poc.test.task.stream.handler.StreamWorkingHandler; 
import org.poc.test.task.stream.handler.TransmissionSource;

public class TestStreamTask {

	static public void main(String[] args) {

		new TestStreamTask().tsStreamTask();

	}

	public void tsStreamTask() {

		TransmissionSource source = TransmissionSource.generate();

		final Task task = Task.generate();
		task.getEventBus().unregister(TaskEvent.Acceptable.class);
		task.getEventBus().unregister(TaskEvent.Working.class);

		task.getEventBus().register(TaskEvent.Acceptable.class, new StreamAcceptableHandler(source));
		task.getEventBus().register(TaskEvent.Working.class, new StreamWorkingHandler(source));

		int n = 100;
		task.start(n);

		System.out.println("--- stream task start  1.------------------------");

		System.out.println("================================================");

		n += 100;

		task.start(n);

		System.out.println("--- stream task start  2.------------------------");

		System.out.println("================================================");

	}

}
