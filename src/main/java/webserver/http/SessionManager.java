package webserver.http;

import webserver.http.exception.NotExistSessionIdException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private Map<String, HttpSession> session;

    private SessionManager() {
        session = new ConcurrentHashMap<>();
    }

    public static SessionManager getInstance() {
        return LazyHolder.httpSessionManager;
    }

    private static class LazyHolder {
        private static final SessionManager httpSessionManager = new SessionManager();
    }

    public String setAttribute(String key, Object object) {
        HttpSession httpSession = new HttpSession();
        String sessionId = httpSession.getId();

        httpSession.setAttribute(key, object);

        session.put(sessionId, httpSession);
        return sessionId;
    }

    public HttpSession getSession(String sessionId) {
        if (!session.containsKey(sessionId)) {
            throw new NotExistSessionIdException();
        }
        return session.get(sessionId);
    }
}
