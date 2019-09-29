package http.session.support;

import http.support.HttpCookie;
import http.session.HttpSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    public static final String SESSION_NAME = "SESSIONID";

    private final Map<String, HttpSession> httpSessions = new ConcurrentHashMap<>();
    private SessionKeyGenerator generator;

    public SessionManager(final SessionKeyGenerator generator) {
        this.generator = generator;
    }

    public HttpSession getSession(final HttpCookie cookie) {
        String sessionId = cookie.getValue(SESSION_NAME);

        if (sessionId == null || !httpSessions.containsKey(sessionId)) {
            return makeNewSession(sessionId);
        }
        return httpSessions.get(sessionId);
    }

    private HttpSession makeNewSession(final String sessionId) {
        UUID uuid = generator.generate(sessionId);
        HttpSession httpSession = new HttpSession(uuid);
        httpSessions.put(uuid.toString(), httpSession);
        return httpSession;
    }

    public void clearSession() {
        httpSessions.clear();
    }
}
