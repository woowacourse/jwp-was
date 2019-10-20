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

    public HttpSession getHttpSession(String id) {
        return sessionGroup.get(id) == null
                ? getNewHttpSession()
                : sessionGroup.get(id);
    }

    public HttpSession getNewHttpSession() {
        String id = idGenerator.generateId();
        HttpSession session = new HttpSession(id);

        sessionGroup.put(session.getId(), session);
        return session;
    }
}
