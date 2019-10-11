package http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<String, HttpSession> sessionGroup;
    private IdGenerator idGenerator;

    public SessionManager(IdGenerator idGenerator) {
        sessionGroup = new HashMap<>();
        this.idGenerator = idGenerator;
    }

    public HttpSession getHttpSession(String sessionId) {
        return sessionGroup.get(sessionId);
    }

    public HttpSession getNewHttpSession() {
        String id = idGenerator.generateId();
        HttpSession session = new HttpSession(id);

        add(session);
        return session;
    }

    private void add(HttpSession session) {
        sessionGroup.put(session.getId(), session);
    }
}
