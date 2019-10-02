package webserver;

import webserver.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static String createSession() {
        String uuid = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(uuid);
        sessions.put(uuid, httpSession);
        return uuid;
    }

    public static HttpSession getSession(String uuid) {
        return sessions.get(uuid);
    }
}
