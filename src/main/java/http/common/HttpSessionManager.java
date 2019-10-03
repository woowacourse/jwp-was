package http.common;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpSessionManager {

    public static final String JSESSIONID = "JSESSIONID";
    private static final ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession getSession(String sessionId) {
        HttpSession httpSession = sessions.get(sessionId);
        if (httpSession == null) {
            return createSession();
        }
        return httpSession;
    }

    private static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessions.put(sessionId, httpSession);
        return httpSession;
    }
}
