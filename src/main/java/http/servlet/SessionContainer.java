package http.servlet;

import java.util.HashMap;
import java.util.Map;

public class SessionContainer {
    public static final String SESSION_KEY_FOR_COOKIE = "session_id";

    private final Map<String, HttpSession> sessionContainer = new HashMap<>();

    private SessionContainer() {
    }

    public static SessionContainer getInstance() {
        return Holder.INSTANCE;
    }

    public void put(String sessionId, HttpSession httpSession) {
        sessionContainer.put(sessionId, httpSession);
    }

    private static class Holder {
        private static final SessionContainer INSTANCE = new SessionContainer();
    }
}
