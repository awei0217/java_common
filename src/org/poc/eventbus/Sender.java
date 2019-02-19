package org.poc.eventbus;

import org.poc.async.F; 

public interface Sender {

	<E extends Event> F.Listenable<EventResult> send(E event);

}
