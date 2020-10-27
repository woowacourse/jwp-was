package server.domain.request;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public class HttpSessionStorage {

    private final Map<String, HttpSession> sessionStorage;

    private HttpSessionStorage() {
        this.sessionStorage = new ConcurrentHashMap<>();
    }

    public static HttpSessionStorage getInstance() {
        return Cache.HTTP_SESSION_STORAGE;
    }

    public HttpSession getSession(String sessionKey) {
        if (sessionStorage.containsKey(sessionKey)) {
            return sessionStorage.get(sessionKey);
        }
        return createSession();
    }

    private HttpSession createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid.toString());
        sessionStorage.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    private static class Cache {

        private static final HttpSessionStorage HTTP_SESSION_STORAGE = new HttpSessionStorage();
    }
}
