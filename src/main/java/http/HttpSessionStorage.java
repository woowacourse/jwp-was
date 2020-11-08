package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {

    private static final Map<String, HttpSession> storage = new HashMap<>();

    public static void save(final String id, final HttpSession httpSession) {
        storage.put(id, httpSession);
    }

    public static boolean isExistById(final String id) {
        return storage.containsKey(id);
    }
}
