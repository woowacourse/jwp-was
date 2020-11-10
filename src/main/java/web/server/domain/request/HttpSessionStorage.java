package web.server.domain.request;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionStorage {

    private Map<String, HttpSession> sessionStorage;

    private HttpSessionStorage() {
        this.sessionStorage = new ConcurrentHashMap<>();
    }

    public static HttpSessionStorage getInstance() {
        return Cache.HTTP_SESSION_STORAGE;
    }

    public HttpSession createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid.toString());
        sessionStorage.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public HttpSession getSession(String key) {
        return sessionStorage.get(key);
    }

    private static class Cache {

        private static final HttpSessionStorage HTTP_SESSION_STORAGE = new HttpSessionStorage();
    }
}
