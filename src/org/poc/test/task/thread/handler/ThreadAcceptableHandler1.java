package org.poc.test.task.thread.handler;

import com.google.common.collect.Maps;
import org.poc.eventbus.EventResult;
import org.poc.task.Task;
import org.poc.task.TaskEvent;
import org.poc.task.handler.TaskHandler;
import org.poc.task.service.AcceptableService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadAcceptableHandler1 extends TaskHandler.Acceptable {

	AcceptableServiceImpl service;

	@Override
	protected <V> AcceptableService getAcceptService(Optional<V> args) {

		return Optional.ofNullable(service).orElseGet(() -> {
			service = new AcceptableServiceImpl();
			service.withSender(ThreadAcceptableHandler1.super.sender);
			return service;
		});
	}

	final class AcceptableServiceImpl extends AcceptableService {
		
		final Map<String, Object> locks = Maps.newConcurrentMap();

		AcceptableServiceImpl() {

		}

		final AtomicBoolean starting = new AtomicBoolean(false);

		/***
		 * locked means this service is working , initialization is complete, just wake up all threads. 
		 * actually locks.size = runningThread.size  is  finish .
		 */
		@Override
		protected EventResult locked(TaskEvent event) {
			if (!starting.get()) {
				starting.lazySet(true);
				return EventResult.UnLock;
			}
			this.ntfAll();
			return EventResult.Lock;
		}

		@Override
		protected EventResult execute(TaskEvent event, EventResult result) {
			// this for test
			for (int i = 0; i < 2; i++) {
				locks.put("" + i, generateExecutor("Th-" + i));
			}
			return EventResult.Success(" independent is running. ");
		}

		int count = 10;

		Object generateExecutor(String name) {
			final Object object = new Object();

			new Thread(name) {
				@Override
				public void run() {

					while (true) {
						Object runObject;
						if (count < 0) {
							runObject = null;
						} else {
							runObject = count--;
						}

						if (runObject == null) {
							onSupply(TaskEvent.generate(Task.Grade.Working));
							try {
								synchronized (object) {
									object.wait();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else {
							System.out.println("--- Name:" + name + " run " + runObject);
						}

					}
				}
			}.start();
			return object;
		}

		void ntfAll() {
			if (!locks.isEmpty()) {
				locks.keySet().stream().forEach(key -> {
					Object lock = locks.get(key);
					synchronized (lock) {
						lock.notify();
					}
				});
			}
		}
	}
}
