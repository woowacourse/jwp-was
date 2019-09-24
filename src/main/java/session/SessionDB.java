package session;

import com.google.common.collect.Maps;

import java.util.Map;

public class SessionDB {
    private static Map<String, HttpSession> sessions = Maps.newHashMap();

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void addSession(HttpSession httpSession) {
        sessions.put(httpSession.getId(), httpSession);
    }
}
