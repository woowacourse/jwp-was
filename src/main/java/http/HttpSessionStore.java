package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionStore {
    private static final Logger log = LoggerFactory.getLogger(HttpSessionStore.class);

    private static Map<String, HttpSession> sessions;

    static {
        sessions = new HashMap<>();
    }

    public static boolean containsSession(String key) {
        return sessions.containsKey(key);
    }

    public static HttpSession getSession(String key) {
        log.debug("session store map: {}", sessions.toString());
        return sessions.containsKey(key) ? sessions.get(key) : createSession();
    }

    private static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new HttpSession(sessionId));
        log.debug("create new session! session id : {} , session : {}" , sessionId, sessions.get(sessionId));
        return sessions.get(sessionId);
    }
}
