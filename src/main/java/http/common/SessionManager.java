package http.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    public static final String SESSION_ID = "JSESSIONID";
    public static final int DEFAULT_EXPIRE_TIME = 30;
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final Map<UUID, HttpSession> managedSessions = new HashMap<>();

    public static HttpSession getSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid, DEFAULT_EXPIRE_TIME);
        managedSessions.put(uuid, httpSession);

        logger.info("session create {}", httpSession);
        return httpSession;
    }

    public static HttpSession getSession(UUID uuid) {
        return managedSessions.get(uuid);
    }

    public static void removeSession(UUID uuid) {
        managedSessions.remove(uuid);
    }
}
