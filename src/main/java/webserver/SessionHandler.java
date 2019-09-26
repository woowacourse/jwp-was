package webserver;

import http.common.HttpSession;
import http.exception.InvalidSessionExceptioon;

import java.util.HashMap;
import java.util.Map;

public class SessionHandler {
    private static SessionHandler sessionHandler;
    private Map<String, HttpSession> session;

    public SessionHandler() {
        session = new HashMap<>();
    }

    public static SessionHandler getInstance() {
        if (sessionHandler == null) {
            sessionHandler = new SessionHandler();
            return sessionHandler;
        }
        return sessionHandler;
    }

    public void addSession(String sessionId, HttpSession session) {
        this.session.put(sessionId, session);
    }

    public HttpSession getSession(String sessionId) {
        if (session.containsKey(sessionId)) {
            return session.get(sessionId);
        }
        throw new InvalidSessionExceptioon("존재하지 않는 세션 ID 입니다.");
    }
}
