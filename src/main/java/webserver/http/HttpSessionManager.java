package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpSessionManager implements SessionManager {
    private final Map<String, HttpSession> map = new HashMap<>();

    private HttpSessionManager() {
    }

    public Optional<HttpSession> getSession(final String sessionId) {
        return Optional.ofNullable(map.get(sessionId));
    }

    public HttpSession createSession() {
        final HttpSession httpSession = new HttpSession();
        map.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static HttpSessionManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpSessionManager INSTANCE = new HttpSessionManager();
    }
}
