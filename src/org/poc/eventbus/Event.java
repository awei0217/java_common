package org.poc.eventbus;

/***
 * 
 * @author gaoqi3 2018.03.07
 *
 */

public interface Event {

	<T> T getContent();

	<T> T getProperty(String key);

}
