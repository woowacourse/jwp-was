package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {
    private static Map<String, HttpSession> storage = new HashMap<>();

    public static HttpSession addSession(HttpSession httpSession) {
        storage.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static HttpSession create() {
        HttpSession httpSession = new HttpSession();
        return addSession(httpSession);
    }

    public static HttpSession getSession(String sessionId) {
        return storage.get(sessionId);
    }

    public static void deleteSession(String sessionId) {
        storage.remove(sessionId);
    }
}
