package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStore {

    private static final Map<String, HttpSession> HTTP_SESSION_STORE = new HashMap<>();

    private HttpSessionStore() {
    }

    public static HttpSession getSession(String id) {
        return HTTP_SESSION_STORE.get(id);
    }

    public static HttpSession addSession(HttpSession httpSession) {
        return HTTP_SESSION_STORE.put(httpSession.getId(), httpSession);
    }

    public static boolean isContains(HttpSession httpSession) {
        return HTTP_SESSION_STORE.containsValue(httpSession);
    }

    public static void delete(String key) {
        HTTP_SESSION_STORE.remove(key);
    }
}
