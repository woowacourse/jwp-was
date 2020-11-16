package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStore {

    private final static Map<String, HttpSession> httpSessionStore = new HashMap<>();

    public static HttpSession getSession(String id) {
        return httpSessionStore.get(id);
    }

    public static HttpSession addSession(HttpSession httpSession) {
        return httpSessionStore.put(httpSession.getId(), httpSession);
    }

    public static HttpSession create() {
        WebSession webSession = new WebSession();
        addSession(webSession);

        return webSession;
    }

    public static boolean isContains(String key) {
        return httpSessionStore.containsKey(key);
    }

    public static void delete(String key) {
        httpSessionStore.remove(key);
    }
}
