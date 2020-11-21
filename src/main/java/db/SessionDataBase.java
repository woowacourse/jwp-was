package db;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpSession;

public class SessionDataBase {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static void addHttpSession(HttpSession httpSession) {
        sessions.put(httpSession.getId(), httpSession);
    }

    public static HttpSession findHttpSessionById(String id) {
        return sessions.get(id);
    }
}
