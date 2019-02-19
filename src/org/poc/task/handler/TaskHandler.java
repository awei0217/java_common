package org.poc.task.handler;

import java.util.Optional; 

import org.poc.eventbus.Event;
import org.poc.eventbus.EventBus;
import org.poc.eventbus.EventResult;
import org.poc.task.Task;
import org.poc.task.TaskEvent;
import org.poc.task.service.AcceptableService;
import org.poc.task.service.TaskService;
import org.poc.task.service.WorkingService;

/****
 * 
 * @author gaoqi3
 *
 */

public abstract class TaskHandler extends EventBus.EventHandler {

	@Override
	protected <E extends Event> EventResult handle(E event) {
		TaskEvent evt = (TaskEvent) event;
		return Optional.ofNullable((TaskService) getTaskService(Optional.ofNullable(evt.getArgs()))).map(service -> {
			return service.accept(evt);
		}).orElse(EventResult.Error(null, "no match handler service."));
	}

	protected <S extends TaskService> S getTaskService() {
		return getTaskService(Optional.empty());
	}

	abstract protected <V, S extends TaskService> S getTaskService(Optional<V> args);

	// ----------------------------------------------

	static public class Initializing extends TaskHandler {

		final TaskService defService = new TaskService.Impl() {

			final EventResult defResult = EventResult.Success(Task.Grade.Pending);

			@Override
			protected EventResult execute(TaskEvent event, EventResult result) {
				System.out.println("-- Default initializing service action... " + "Event:{" + event.toString() + "}");
				return result.isSuccessful() ? defResult : result;
			}
		}.withSender(sender);

		@SuppressWarnings("unchecked")
		@Override
		protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
			return (S) defService;
		}

	}

	static public class Waiting extends TaskHandler {

		final TaskService defService = new TaskService.Impl() {

			final EventResult defResult = EventResult.Success(" waiting ok...");

			@Override
			protected EventResult execute(TaskEvent event, EventResult result) {
				System.out.println("-- Default waiting service action... " + "Event:{" + event.toString() + "}");
				return result.isSuccessful() ? defResult : result;
			}
		}.withSender(sender);;

		@SuppressWarnings("unchecked")
		@Override
		protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
			return (S) defService;
		}
	}

	static public class Pending extends TaskHandler {

		final TaskService defService = new TaskService.Impl() {
			final EventResult defResult = EventResult.Ok;

			@Override
			protected EventResult execute(TaskEvent event, EventResult result) {
				System.out.println("-- Default pending service action... " + "Event:{" + event.toString() + "}");
				return result.isSuccessful() ? defResult : result;
			}
		}.withSender(sender);

		@SuppressWarnings("unchecked")
		@Override
		protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
			return (S) defService;
		}
	}

	static public abstract class Working extends TaskHandler {

		final TaskService defService = new WorkingService() {

			@Override
			public EventResult onWorking(TaskEvent event) {
				System.out.println("-- Default working service action... " + "Event:{" + event.toString() + "}");
				return EventResult.Ok;
			}

		}.withSender(sender);

		@SuppressWarnings("unchecked")
		@Override
		protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
			TaskService s = getWorkingService(args);
			return (S) (s == null ? defService : s);
		}

		abstract protected <V> WorkingService getWorkingService(Optional<V> args);
	}

	static public abstract class Acceptable extends TaskHandler {

		final TaskService defService = new TaskService.Impl() {
			@Override
			protected EventResult execute(TaskEvent event, EventResult result) {
				System.out.println("-- Default acceptable service action... " + "Event:{" + event.toString() + "}");
				return EventResult.Ok;
			}
		};
 
		@SuppressWarnings("unchecked")
		@Override
		protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
			AcceptableService s = getAcceptService(args);
			return (S) (s == null ? defService : s);
		}

 		abstract protected <V> AcceptableService getAcceptService(Optional<V> args);

	}

	static public class Errors extends TaskHandler {

		final TaskService defService = new TaskService.Impl() {

			@Override
			protected EventResult execute(TaskEvent event, EventResult result) {
				System.out.println("--error: {" + event.toString() + "}\n");
				return result;
			}
		}.withSender(sender);

		@SuppressWarnings("unchecked")
		@Override
		protected <V, S extends TaskService> S getTaskService(Optional<V> args) {
			return (S) defService;
		}

	}

}
