package org.poc.reactor;

import java.util.Optional;

import org.reactivestreams.Subscription;

import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public interface EventSource {

	public interface Event {

		<T> T getMessage();

		static <T> Event generate(T message) {
			return new Event() {
				@SuppressWarnings("unchecked")
				@Override
				public T getMessage() {
					return (T) message;
				}
			};
		}
	}

	abstract class AbsSubscriber extends BaseSubscriber<Event> {

		@Override
		protected void hookOnSubscribe(Subscription subscription) {

			request(1);
		}

		@Override
		protected void hookOnComplete() {
		}

		@Override
		protected void hookOnNext(Event event) {
			onEvent(event);

			request(1);
		}

		@Override
		protected void hookOnError(Throwable throwable) {
			onErrors(throwable);
		}

		protected abstract void onErrors(Throwable throwable);

		protected abstract void onEvent(Event event);

	}

	interface EventEmitter extends Disposable {

		void emit(long requestCount);

		EventEmitter with(FluxSink<Object> sink);

		abstract class Impl implements EventEmitter {

			FluxSink<Object> sink;

			@Override
			public EventEmitter with(FluxSink<Object> sink) {
				this.sink = sink;
				return this;
			}

			protected <T> Impl accept(final T value) {

				Optional.ofNullable(sink).map(v -> {
					if (!v.isCancelled()) {
						v.next(value);
					}
					return v;
				});

				return this;
			}

			@Override
			public void dispose() {
				if (!sink.isCancelled())
					sink.complete();
			}

		}

	}

	Flux<Event> getStreams();

	public abstract class EmitterSource implements EventSource {

		final EventEmitter emitter;

		Flux<Event> streams;

		protected EmitterSource(FluxSink.OverflowStrategy strategy) {
			this(new EventEmitter.Impl() {
				@Override
				public void emit(long requestCount) {
				}
			}, strategy);
		}

		protected EmitterSource(EventEmitter emitter, FluxSink.OverflowStrategy strategy) {
			this.emitter = emitter;

			this.streams = Flux.create(sink -> {
				sink.onRequest(this.emitter.with(sink)::emit);
			}, strategy).map(value -> Event.generate(value));

		}

		@Override
		public Flux<Event> getStreams() {
			return streams;
		}

		public EventEmitter getEmitter() {
			return emitter;
		}

	}

}
