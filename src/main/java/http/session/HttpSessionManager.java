package http.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    private HttpSessionManager() {

    }

    public static HttpSessionManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpSessionManager INSTANCE = new HttpSessionManager();
    }

    public HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public HttpSession createSession(SessionKeyGenerator sessionKeyGenerator) {
        String sessionId = sessionKeyGenerator.create();
        HttpSession httpSession = new HttpSession(sessionId);
        sessions.put(sessionId, httpSession);
        return httpSession;
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
