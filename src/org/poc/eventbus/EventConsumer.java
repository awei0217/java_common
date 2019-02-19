package org.poc.eventbus;

import org.poc.async.F;

/***
 * 
 * @author gaoqi3 2018.03.07
 *
 */

public final class EventConsumer {

	static EventConsumer DefErrorConsumer = new EventConsumer(new EventBus.EventHandler() {
		@Override
		protected <E extends Event> EventResult handle(E event) {
			return EventResult.Error(null, event.getClass().getName() + " no match handler .");
		}
	});

	String eventName;

	final EventBus.EventHandler handler;

	EventConsumer(EventBus.EventHandler handler) {
		this.handler = handler;
		init();
	}

	void init() {

	}

	public F.Listenable<EventResult> accept(Event event) {
		return F.immediate(handler.apply(event));
	}

}
