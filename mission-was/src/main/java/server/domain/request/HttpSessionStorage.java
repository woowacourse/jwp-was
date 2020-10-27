package server.domain.request;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public class HttpSessionStorage {

    private final Map<String, HttpSevletSession> sessionStorage;

    private HttpSessionStorage() {
        this.sessionStorage = new ConcurrentHashMap<>();
    }

    public static HttpSessionStorage getInstance() {
        return Cache.HTTP_SESSION_STORAGE;
    }

    public HttpSevletSession getSession(String sessionKey) {
        if (sessionStorage.containsKey(sessionKey)) {
            return sessionStorage.get(sessionKey);
        }
        return createSession();
    }

    private HttpSevletSession createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSevletSession httpSevletSession = new HttpSevletSession(uuid.toString());
        sessionStorage.put(httpSevletSession.getId(), httpSevletSession);
        return httpSevletSession;
    }

    private static class Cache {

        private static final HttpSessionStorage HTTP_SESSION_STORAGE = new HttpSessionStorage();
    }
}
