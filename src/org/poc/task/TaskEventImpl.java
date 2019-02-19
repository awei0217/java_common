package org.poc.task;

import java.util.Map;

import com.google.common.collect.Maps;

/****
 * 
 * @author gaoqi3
 *
 */

abstract class TaskEventImpl implements TaskEvent {

	Map<String, Object> cache = null;

	Object content;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getContent() {
		return (T) content;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		return cache == null ? null : (T) cache.get(key);
	}

	<T> void _add(String key, T object) {
		if (cache == null) {
			this.cache = Maps.newConcurrentMap();
		}
		cache.put(key, object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends TaskEvent, T> E add(String key, T object) {
		_add(key, object);
		return (E) this;
	}

	@Override
	public String toString() {
		String _cacheValue = "null";
		if (cache != null) {
			_cacheValue = "";

			for (String key : cache.keySet()) {
				_cacheValue += key + ":" + cache.get(key) + ",";
			}
		}
		return "{" + "content:" + content + ",cache:" + _cacheValue + "} ";
	}

}
