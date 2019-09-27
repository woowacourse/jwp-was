package dev.luffy.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {

    private Map<String, HttpSession> httpSessionStorage;

    private HttpSessionStorage() {
        httpSessionStorage = new HashMap<>();
    }

    public static HttpSessionStorage getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpSessionStorage INSTANCE = new HttpSessionStorage();
    }

    public void createSession(String sessionId) {
        HttpSession httpSession = new HttpSession(sessionId);
        httpSessionStorage.put(sessionId, httpSession);
    }

    public HttpSession getSession(String id) {
        return httpSessionStorage.get(id);
    }

    public void removeSession(String id) {
        httpSessionStorage.remove(id);
    }
}
