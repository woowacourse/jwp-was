package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, HttpSession> sessionManager = new HashMap<>();

    public static HttpSession getSession(String uuid) {
        return sessionManager.containsKey(uuid) ? sessionManager.get(uuid) : createSession(uuid);
    }

    private static HttpSession createSession(String uuid) {
        final HttpSession httpSession = new HttpSession(uuid);
        sessionManager.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    static void remove(String uuid) {
        sessionManager.remove(uuid);
    }
}
