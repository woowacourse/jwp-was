package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {

    private final Map<String, HttpSession> storage;

    private HttpSessionStorage() {
        this.storage = new HashMap<>();
    }

    public static HttpSessionStorage getInstance() {
        return HttpSessionStorageHolder.INSTANCE;
    }

    public void save(final String id, final HttpSession httpSession) {
        this.storage.put(id, httpSession);
    }

    public boolean isExistById(final String id) {
        return this.storage.containsKey(id);
    }

    private static class HttpSessionStorageHolder {
        private static final HttpSessionStorage INSTANCE = new HttpSessionStorage();
    }
}
