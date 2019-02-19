package org.poc.test.task.thread.handler;

import org.poc.eventbus.EventResult;
import org.poc.task.TaskEvent;
import org.poc.task.handler.TaskHandler;
import org.poc.task.service.WorkingService;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadWorkingHandler1 extends TaskHandler.Working {

	WorkingServiceImpl service;

	@Override
	protected <V> WorkingService getWorkingService(Optional<V> args) {
		return Optional.ofNullable(service).orElseGet(() -> {
			service = new WorkingServiceImpl();
			service.withSender(ThreadWorkingHandler1.super.sender);
			return service;
		});
	}

	final class WorkingServiceImpl extends WorkingService {

		AtomicInteger count = new AtomicInteger(20);

		@Override
		public EventResult onWorking(TaskEvent event) {
			int value = count.getAndDecrement();
			if (value < 0) {
				System.out.println("--Working1 onWork , for test " + value);
				count.set(200);
				return EventResult.Success( "ThreadWorkingHandler.WorkingServiceImpl work finished ");
			}
			return EventResult.Ok; 
		}

	}

}
