package http.session.support;

import http.request.HttpCookie;
import http.request.HttpRequest;
import http.session.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    public static final String SESSION_NAME = "SESSIONID";
    private final Map<String, HttpSession> httpSessions = new HashMap<>();

    public HttpSession getSession(final HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(SESSION_NAME);

        if (sessionId == null) {
            return makeNewSession();
        }

        if (httpSessions.containsKey(sessionId)) {
            return httpSessions.get(sessionId);
        }
        return new HttpSession(UUID.fromString(sessionId));
    }

    public HttpSession getSession(final HttpCookie cookie) {
        String sessionId = cookie.getValue(SESSION_NAME);

        if (sessionId == null) {
            return makeNewSession();
        }

        if (httpSessions.containsKey(sessionId)) {
            return httpSessions.get(sessionId);
        }
        return new HttpSession(UUID.fromString(sessionId));
    }

    private HttpSession makeNewSession() {
        UUID uuid =  UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);
        httpSessions.put(uuid.toString(), httpSession);
        return httpSession;
    }
}
