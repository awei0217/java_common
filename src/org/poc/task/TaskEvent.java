package org.poc.task;

import org.poc.eventbus.Event;

/***
 * 
 * @author gaoqi3
 *
 */

public interface TaskEvent extends Event {

	@SuppressWarnings("unchecked")
	static <E extends TaskEvent> E generate(Task.Grade grade) {
		switch (grade) {
		case Init:
			return (E) new Initializing();
		case Non:
			return (E) new Initializing();
		case Pending:
			return (E) new Pending();
		case Waiting:
			return (E) new Waiting();
		case Working:
			return (E) new Working();
		case Error:
			return (E) new Errors();
		}
		return null;
	}

	<E extends TaskEvent, T> E add(String key, T object);

	default <T> TaskEvent withArgs(T args) {
		return add("_$Args$_", args);
	}

	default <T> T getArgs() {
		return this.getProperty("_$Args$_");
	}

	class Initializing extends TaskEventImpl {

	}

	class Pending extends TaskEventImpl {

	}

	class Working extends TaskEventImpl {

	}

	class Waiting extends TaskEventImpl {

	}

	class Errors extends TaskEventImpl {

	}

	class Acceptable extends TaskEventImpl {

		ExecuteModel execModel = ExecuteModel.Centralization;

		public ExecuteModel getExecModel() {
			return execModel;
		}

	}

	enum ExecuteModel {
		Centralization, Independent
	}

}
