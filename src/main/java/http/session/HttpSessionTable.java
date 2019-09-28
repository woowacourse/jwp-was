package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionTable {
    private static final Map<String, HttpSession> sessionTable = new HashMap<>();

    public static HttpSession getSession(String sessionId, SessionIdStrategy strategy) {
        HttpSession session = sessionTable.get(sessionId);
        if (session == null) {
            session = new HttpSession();
            sessionTable.put(strategy.generateSessionId(), session);
        }
        return session;
    }
}
