package webserver;

import webserver.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static Map<UUID, HttpSession> sessions = new HashMap<>();

    public static String createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);
        sessions.put(uuid, httpSession);
        return uuid.toString();
    }

    public static HttpSession getSession(String uuid) {
        return sessions.get(UUID.fromString(uuid));
    }
}
