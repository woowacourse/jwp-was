package webserver.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
	private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

	private SessionStorage() {
	}

	public static String create(String userId, Object value) {
		HttpSession httpSession = new HttpSession();
		httpSession.setAttributes(userId, value);
		sessions.put(httpSession.getId(), httpSession);
		return httpSession.getId();
	}

	public static HttpSession findBy(String sessionId) {
		return sessions.get(sessionId);
	}

	public static void loginBy(String sessionId) {
		HttpSession httpSession = sessions.get(sessionId);
		httpSession.setAttributes("logined", true);
	}

	public static boolean contain(String key) {
		return sessions.containsKey(key);
	}

	public static void clear() {
		sessions.clear();
	}
}
