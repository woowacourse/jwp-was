package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {

    public static final String JSESSIONID = "JSESSIONID";
    private static final Map<String, HttpSession> sessions = new HashMap<>();

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
