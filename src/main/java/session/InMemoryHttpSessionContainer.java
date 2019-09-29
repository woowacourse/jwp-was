package session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class InMemoryHttpSessionContainer implements HttpSessionContainer {
    private static Map<String, HttpSession> sessions;
    private static InMemoryHttpSessionContainer instance;

    private InMemoryHttpSessionContainer() {
        sessions = Maps.newHashMap();
    }

    public static InMemoryHttpSessionContainer getInstance() {
        if (instance == null) {
            instance = new InMemoryHttpSessionContainer();
        }
        return instance;
    }

    @Override
    public HttpSession findSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public HttpSession createSession() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}


