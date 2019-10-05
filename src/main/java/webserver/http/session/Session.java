package webserver.http.session;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import webserver.exception.NotFoundSessionAttribute;

public class Session {
	private Map<String, Object> attributes = new LinkedHashMap<>();

	public Session(Object sessionId) {
		attributes.put("id", sessionId);
	}

	public Object getId() {
		return attributes.get("id");
	}

	public void setAttribute(String name, Object value) {
		if (Objects.nonNull(value)) {
			attributes.put(name, value);
			return;
		}
		throw new NullPointerException();
	}

	public Object getAttribute(String name) {
		Object value = attributes.get(name);

		if (Objects.nonNull(name) && Objects.nonNull(value)) {
			return value;
		}
		throw new NotFoundSessionAttribute();
	}

	public void removeAttribute(String name) {
		Object value = attributes.get(name);

		if (Objects.nonNull(name) && Objects.nonNull(value)) {
			attributes.remove(value);
			return;
		}
		throw new NotFoundSessionAttribute();
	}

	public void invalidate() {
		Iterator<String> iter = attributes.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			if (!"id".equals(key)) {
				iter.remove();
			}
		}
	}
}
