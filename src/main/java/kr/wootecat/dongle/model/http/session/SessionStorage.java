package kr.wootecat.dongle.model.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {

    private final Map<String, Session> sessions;

    private SessionStorage(Map<String, Session> sessions) {
        this.sessions = sessions;
    }

    public static SessionStorage ofEmpty() {
        return new SessionStorage(new HashMap<>());
    }

    public void insert(String sessionId) {
        sessions.put(sessionId, SessionFactory.createSession(sessionId));
    }
}
