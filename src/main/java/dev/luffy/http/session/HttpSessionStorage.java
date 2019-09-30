package dev.luffy.http.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpSessionStorage {

    private ConcurrentMap<String, HttpSession> httpSessionStorage;

    private HttpSessionStorage() {
        httpSessionStorage = new ConcurrentHashMap<>();
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
