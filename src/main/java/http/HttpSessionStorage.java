package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {

    private static final Map<String, HttpSession> storage = new HashMap<>();

    public static void save(final String id, final HttpSession httpSession) {
        if (storage.containsKey(id)) {
            return ;
        }
        storage.put(id, httpSession);
    }

    public static HttpSession getHttpSessionById(final String id) {
        return storage.get(id);
    }

    public static boolean isExistById(final String id) {
        return storage.containsKey(id);
    }
}
