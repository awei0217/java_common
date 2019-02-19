package org.poc.test.task.stream.handler;

import org.poc.task.Task;
import org.poc.task.TaskEvent;

public class StreamEvent {

	static TaskEvent.Working dataProcessFinished = (TaskEvent.Working) TaskEvent.generate(Task.Grade.Working)
			.withArgs(true);

}
