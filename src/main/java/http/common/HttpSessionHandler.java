package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionHandler {
    private static final Map<UUID, HttpSession> sessionsContext;

    static {
        sessionsContext = new HashMap<>();
    }

    public static HttpSession createSession() {
        UUID uuid = UUID.randomUUID();
        sessionsContext.put(uuid, new HttpSession(uuid));
        return sessionsContext.get(uuid);
    }

    public static String getSessionId() {
        return UUID.randomUUID().toString();
    }

    public static void removeSession(UUID uuid) {
        sessionsContext.remove(uuid);
    }
}
