package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {
    private static Map<String, HttpSession> storage = new HashMap<>();

    public static HttpSession addSession(HttpSession httpSession) {
        return storage.put(httpSession.getId(), httpSession);
    }

    public static HttpSession create() {
        HttpSession httpSession = new HttpSession();
        return addSession(httpSession);
    }

    public static HttpSession getSession(String sessionId) {
        return storage.get(sessionId);
    }
}
