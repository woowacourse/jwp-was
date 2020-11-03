package webserver.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
	private final String id;
	private final Map<String, Object> attributes = new ConcurrentHashMap<>();

	public HttpSession() {
		id = SessionIdGenerator.generate();
	}

	public String getId() {
		return id;
	}

	public void setAttributes(String name, Object value) {
		attributes.put(name, value);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public void invalidate() {
		attributes.clear();
	}
}
