package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SessionStorage {
	private static final Map<String, HttpSession> sessions = new HashMap<>();

	public static HttpSession getSession(String id) {
		if (Objects.isNull(id)) {
			id = UUID.randomUUID().toString();
		}
		if (!sessions.containsKey(id)) {
			sessions.put(id, new HttpSession(id));
		}
		return sessions.get(id);
	}
}
