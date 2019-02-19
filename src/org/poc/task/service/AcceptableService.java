package org.poc.task.service;
 
import org.poc.task.TaskEvent;

public abstract class AcceptableService extends TaskService.Impl {

	protected void onSupply(TaskEvent.Working working) {
		sender.send(working);
	}

}
