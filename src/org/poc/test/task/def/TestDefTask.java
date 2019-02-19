package org.poc.test.task.def;

import org.junit.Test;
import org.poc.task.Task;

public class TestDefTask {

	@Test
	public void tsTaskImpl() {
		Task defSetTask = Task.generate();
		defSetTask.start("1000 ");
	}
}
