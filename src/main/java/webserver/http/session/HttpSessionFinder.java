package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionFinder {
    private static final Map<String, HttpSession> httpSessions = new HashMap<>();

    private HttpSessionFinder() {
    }

    public static void createSession() {
        String sessionId = String.valueOf(UUID.randomUUID());
        HttpSession httpSession = new HttpSession(sessionId);

        httpSessions.put(sessionId, httpSession);
    }

    public static HttpSession findSession(String sessionId) {
        return httpSessions.get(sessionId);
    }
}
