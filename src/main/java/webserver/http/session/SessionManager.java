package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, HttpSession> sessionManager = new HashMap<>();

    public static HttpSession getSession() {
        return createSession();
    }

    public static HttpSession getSession(String uuid) {
        return sessionManager.containsKey(uuid) ? sessionManager.get(uuid) : createSession();
    }

    private static HttpSession createSession() {
        final HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        sessionManager.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    static void remove(String uuid) {
        sessionManager.remove(uuid);
    }
}
