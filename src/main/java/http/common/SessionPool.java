package http.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionPool {
    private static final Logger logger = LoggerFactory.getLogger(SessionPool.class);
    private static final Map<UUID, HttpSession> sessionPool = new HashMap<>();

    public static HttpSession addSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);
        sessionPool.put(uuid, httpSession);

        logger.info("session create {}", httpSession);
        return httpSession;
    }

    public static void removeSession(UUID uuid) {
        sessionPool.remove(uuid);
    }
}
