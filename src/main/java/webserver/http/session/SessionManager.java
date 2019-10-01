package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, HttpSession> sessionManager = new HashMap<>();

    public static HttpSession getSession() {
        return createSession();
    }

    public static HttpSession getSession(String uuid) {
        return sessionManager.get(uuid);
    }

    private static HttpSession createSession() {
        final HttpSession httpSession = new HttpSession();
        sessionManager.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}
