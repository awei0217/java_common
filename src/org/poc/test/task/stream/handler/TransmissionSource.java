package org.poc.test.task.stream.handler;

import org.poc.reactor.EventSource;
import org.poc.reactor.Transmission;

import reactor.core.publisher.FluxSink.OverflowStrategy;

public class TransmissionSource extends EventSource.EmitterSource {

	final Transmission transmission;

	protected TransmissionSource(final Transmission transmission, EventEmitter emitter, OverflowStrategy strategy) {
		super(emitter, strategy);
		this.transmission = transmission;
	}

	static public TransmissionSource generate() {
		final Transmission transmission = Transmission.generate();

		return new TransmissionSource(transmission, new EventEmitter.Impl() {

			@Override
			public void emit(long requestCount) {
				for (int i = 0; i < requestCount; i++)
					this.accept(transmission.getReceiver().accept());
			}
		}, OverflowStrategy.BUFFER);
	}

}
