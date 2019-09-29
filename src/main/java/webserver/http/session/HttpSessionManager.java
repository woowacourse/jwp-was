package webserver.http.session;

import webserver.http.session.exception.InvalidSessionKeyException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private final Map<String, HttpSession> session;

    private HttpSessionManager() {
        this.session = new ConcurrentHashMap<>();
    }

    public static HttpSessionManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpSessionManager INSTANCE = new HttpSessionManager();
    }

    public HttpSession getHttpSession(final String key) {
        validKey(key);
        return session.get(key);
    }

    private void validKey(final String key) {
        if (key == null) {
            throw new InvalidSessionKeyException();
        }
    }

    public HttpSession createSession(final GeneratedId generatedId) {
        String id = generatedId.create();
        HttpSession httpSession = new HttpSession(id);

        return session.putIfAbsent(id, httpSession);
    }
}
