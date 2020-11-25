package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManagement {
    private static final Map<String, HttpSession> sessions;

    static {
        sessions = new HashMap<>();
    }

    public static void put(String name, HttpSession httpSession) {
        sessions.put(name, httpSession);
    }

    public static HttpSession get(String name) {
        return sessions.get(name);
    }
}
