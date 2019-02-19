package org.poc.task.service;

import java.util.concurrent.Callable;

import org.poc.async.F;
import org.poc.eventbus.EventResult;
import org.poc.task.Task;
import org.poc.task.TaskEvent;

public abstract class WorkingService extends TaskService.Impl {

	final EventResult defResult = EventResult.Success(" working ok ... ");

	@Override
	protected EventResult execute(TaskEvent event, EventResult result) {

		if (result.isSuccessful()) {
			F.async(F.submit(new Callable<EventResult>() {

				@Override
				public EventResult call() throws Exception {
					return onWorking(event);
				}

			}), new F.Callback<EventResult>() {

				@Override
				public void onFailure(Throwable arg0) {
					// ----------
				}

				@Override
				public void onSuccess(EventResult result) {
					if (result.isSuccessful()) {
						sender.send(Task.AcceptWorkingFlag);
					} else {
						sender.send(TaskEvent.generate(Task.Grade.Error).withArgs(result));
					}
				}

			});
		}

		return defResult;
	}

	abstract public EventResult onWorking(TaskEvent event);
}
