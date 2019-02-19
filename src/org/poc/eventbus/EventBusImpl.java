package org.poc.eventbus;

import org.poc.async.F;
import org.poc.async.F.Executor;
import org.poc.async.F.Listenable;

/***
 * 
 * @author gaoqi3 2018.03.07
 *
 */

final class EventBusImpl implements EventBus {

    final SubscriberRegistry registry;

    Dispatcher dispatcher = new Dispatcher.Impl() {

    };
    final Executor executor;

    final Sender sender = new Sender() {

        @Override
        public <E extends Event> Listenable<EventResult> send(E event) {
            return post(event);
        }
    };

    EventBusImpl(SubscriberRegistry registry, Executor executor) {
        this.registry = registry.with(this);
        this.executor = executor;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    <E extends Event> Listenable<EventResult> post(E event) {
        return dispatcher.dispatch(event, registry.match(event.getClass()));
    }

    @Override
    public Sender sender() {
        return sender;
    }

    @Override
    public <E extends Event> EventBus register(Class<E> eventClz, EventHandler handler) {
        registry.register(eventClz, handler);
        return this;
    }

    @Override
    public <E extends Event> EventBus unregister(Class<E> eventClz) {
        registry.unregister(eventClz);
        return this;
    }

}
