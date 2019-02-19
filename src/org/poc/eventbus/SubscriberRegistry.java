package org.poc.eventbus;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

/***
 * 
 * @author gaoqi3 2018.03.07
 *
 */
public final class SubscriberRegistry {
	// CopyOnWriteArrayList<EventConsumer>;
	EventBus eb;
	Map<String, EventConsumer> registryMap = Maps.newConcurrentMap();

	SubscriberRegistry with(EventBus eb) {
		this.eb = eb;
		return this;
	}

	public <E extends Event> SubscriberRegistry register(Class<E> eventClz, EventBus.EventHandler handler) {
		return register(getTopicNameByEventClass(eventClz), handler.with(eb));
	}

	public SubscriberRegistry register(String topic, EventBus.EventHandler handler) {

		if (!Optional.ofNullable(registryMap.get(topic)).isPresent()) {
			this.registryMap.put(topic, new EventConsumer(handler));
		}
		
 		

		return this;
	}

	public SubscriberRegistry unregister(String topic) {
		registryMap.remove(topic);
		return this;
	}

	public <E extends Event> SubscriberRegistry unregister(Class<E> eventClz) {

		return unregister(getTopicNameByEventClass(eventClz));
	}

	private <E extends Event> String getTopicNameByEventClass(Class<E> eventClz) {
		return eventClz.getName();
	}

	EventConsumer match(Class<? extends Event> eventClz) {
		return this.registryMap.get(getTopicNameByEventClass(eventClz));
	}

}
