package org.poc.test.task.stream.handler;

import java.util.Optional;

import org.poc.eventbus.EventResult;
import org.poc.reactor.Transmission;
import org.poc.task.Task;
import org.poc.task.TaskEvent;
import org.poc.task.handler.TaskHandler;
import org.poc.task.service.WorkingService;

public class StreamWorkingHandler extends TaskHandler.Working {

	final TransmissionSource source;

	public StreamWorkingHandler(TransmissionSource source) {
		this.source = source;
	}

	WorkingService service;

	@Override
	protected <V> WorkingService getWorkingService(Optional<V> args) {
		return Optional.ofNullable(service).orElseGet(() -> {
			this.service = new StreamWorkingServiceImpl(source.transmission.getGenerator());
			service.withSender(StreamWorkingHandler.super.sender);
			return service;
		});
	}

	final class StreamWorkingServiceImpl extends WorkingService {

		final Transmission.Generator generator;

		public StreamWorkingServiceImpl(Transmission.Generator generator) {
			this.generator = generator;
		}

		Runnable running = null;

		boolean stoped = false;

		boolean dataWorking = true;

		long batch = 0L;

		@Override
		public EventResult onWorking(TaskEvent event) {
			System.out.println("-$$-" + event.toString());
			if (running == null) { // maybe lock if necessary !!
				new Thread(initThread()).start();
			} else {
				if (!check(event)) {
					trusted();
				}
			}

			return EventResult.Ok ;
		}

		boolean check(TaskEvent event) {
			Object flag = event.getArgs();
			if (flag instanceof Boolean) {
				return (boolean) flag;
			}
			return false;
		}

		synchronized void trusted() {
			if (stoped) {
				if (!dataWorking) {
					stoped = false;
					dataWorking = true;
					batch = 0;
					generator.recovery();
					new Thread(initThread()).start();
					System.out.println("--- run thread restart ----------");
					sender.send(Task.AcceptWorkingFlag);
				} else {
					sender.send(Task.WaitingFlg);
					System.out.println("--- run thread stoped " + batch + "------------");
				}
			} else {
				System.out.println("--- run thread starting .............");
			}
		}

		synchronized Runnable initThread() {
			if (running == null) {
				running = new Runnable() {

					@Override
					public void run() {
						while (!stoped) { // mock loop load data

							for (int i = 0; i < 32768; i++) {
								// generate new object for
								generator.accept("mock-test-" + batch + "-" + i);
							}

							batch++;

							if (batch % 1000 == 0) {
								System.out.println("--batch : " + batch);
							}

							if (batch > 100) { // mock the end
								System.out.println(" data generator is finished......");
								generator.ending();
								stoped = true;
								dataWorking = false;

							}
						}

					}

				};

			}
			return running;
		}

	}

}
