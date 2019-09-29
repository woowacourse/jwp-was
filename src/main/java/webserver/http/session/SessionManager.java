package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, HttpSession> sessionManager = new HashMap<>();

    public static HttpSession getSession() {
        return createSession();
    }

    public static HttpSession getSession(String uuid) {
        return hasSessionKey(uuid) ? sessionManager.get(uuid) : createSession();
    }

    private static boolean hasSessionKey(String uuid) {
        return sessionManager.keySet().stream()
                .anyMatch(key -> key.equals(uuid));
    }

    private static HttpSession createSession() {
        final HttpSession httpSession = new HttpSession();
        sessionManager.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}
