package org.poc.test.task.stream.handler;

import java.util.Optional;

import org.poc.eventbus.EventResult;
import org.poc.reactor.EventSource;
import org.poc.reactor.EventSource.Event;
import org.poc.task.TaskEvent;
import org.poc.task.handler.TaskHandler;
import org.poc.task.service.AcceptableService;

public class StreamAcceptableHandler extends TaskHandler.Acceptable {

	final TransmissionSource source;

	public StreamAcceptableHandler(TransmissionSource source) {
		this.source = source;
	}

	AcceptableService service;

	@Override
	protected <V> AcceptableService getAcceptService(Optional<V> args) {

		return Optional.ofNullable(service).orElseGet(() -> {
			this.service = new StreamAcceptableServiceImpl(source);
			this.service.withSender(StreamAcceptableHandler.super.sender);
			return service;
		});
	}

	/***
	 * stream is a continuous processing ,stop without data
	 * 
	 * 
	 */
	class StreamAcceptableServiceImpl extends AcceptableService {

		final TransmissionSource source;

		public StreamAcceptableServiceImpl(TransmissionSource source) {
			this.source = source;
		}

		int count = 0;

		@Override
		protected EventResult execute(TaskEvent event, EventResult result) {

			System.out.println("--&&--" + event.toString() + "," + result.toString());

			source.getStreams().subscribe(new EventSource.AbsSubscriber() {

				@Override
				protected void onEvent(Event event) {
					count++;
					if (count % 200000 == 0) {
						System.out.println(" Stream get Object:" + event.toString() + " ,  on count:" + count);
					}
				}

				@Override
				protected void onErrors(Throwable throwable) {

				}
			});

			return result;
		}

	}
}
