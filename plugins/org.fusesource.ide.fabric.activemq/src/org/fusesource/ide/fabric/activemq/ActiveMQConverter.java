package org.fusesource.ide.fabric.activemq;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import org.fusesource.fon.util.messages.Exchange;
import org.fusesource.fon.util.messages.IExchange;
import org.fusesource.fon.util.messages.Message;


public class ActiveMQConverter {

	private Set<String> nestedProperties = new HashSet<String>(Arrays.asList(
			"BooleanProperties", "ByteProperties", "ShortProperties",
			"IntProperties", "LongProperties", "FloatProperties",
			"DoubleProperties", "StringProperties"));
	private Set<String> bodyKeys = new HashSet<String>(Arrays.asList("Text",
			"Object", "Bytes", "Map"));
	private Set<String> ignoredKeys = new HashSet<String>(Arrays.asList("PropertiesText"));

	public IExchange toExchange(Object object) {
		if (object instanceof CompositeData) {
			CompositeData data = (CompositeData) object;
			return toExchange(data);
		}
		return null;
	}

	public IExchange toExchange(CompositeData data) {
		Message message = new Message();
		Map<String, Object> headers = message.getHeaders();
		Set<String> keySet = data.getCompositeType().keySet();
		for (String key : keySet) {
			Object value = data.get(key);
			// System.out.println("Key: " + key + " value " + value);
			if (ignoredKeys.contains(key)) {
				continue;
			}
			boolean nestedProperty = nestedProperties.contains(key);
			if (nestedProperty && value instanceof TabularData) {
				TabularData td = (TabularData) value;
				Map<String, Object> map = toMap(td);
				putAllNonNull(headers, map);
			} else if (nestedProperty && value instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) value;
				putAllNonNull(headers, map);
			} else if (bodyKeys.contains(key)) {
				message.setBody(value);
			} else if (value != null) {
				headers.put(key, value);
			}
		}
		Set<Entry<String, Object>> entrySet = headers.entrySet();
		return new Exchange(message);
	}

	protected void putAllNonNull(Map<String, Object> headers,
			Map<String, Object> map) {
		Set<Entry<String, Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			Object value = entry.getValue();
			if (value != null) {
				headers.put(entry.getKey(), value);
			}
		}
	}

	private Map<String, Object> toMap(TabularData td) {
		HashMap<String, Object> answer = new HashMap<String, Object>();
		Collection<?> rows = td.values();
		for (Object row : rows) {
			if (row instanceof CompositeData) {
				CompositeData cd = (CompositeData) row;
				// lets assume its just a key & value
				if (true) {
					Object key = cd.get("key");
					if (key != null) {
						Object value = cd.get("value");
						//System.out.println("Got key " + key + " value " + value);
						answer.put(key.toString(), value);
					}
				} else {
					Set<?> keys = cd.getCompositeType().keySet();
					for (Object key : keys) {
						String keyText = key.toString();
						Object value = cd.get(keyText);
						//System.out.println("Got key " + key + " value " + value);
						answer.put(keyText, value);
					}
				}
			}
		}
		return answer;
	}
}
