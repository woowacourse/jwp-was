package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private Map<String, HttpSession> sessions = new HashMap<>();

    public HttpSession getSession(String sessionName) {
        return sessions.get(sessionName);
    }

    public HttpSession createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid.toString());
        sessions.put(uuid.toString(), httpSession);
        return httpSession;
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
