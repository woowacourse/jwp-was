package webserver.http.response;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, HttpSession> session;

    private Session() {
        this.session = new HashMap<>();
    }

    public static Session getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final Session INSTANCE = new Session();
    }

    public HttpSession getHttpSession(final String key) {
        return session.get(key);
    }

    public HttpSession createSession(final GeneratedId generatedId) {
        HttpSession httpSession = new HttpSession(generatedId.create());
        session.put(generatedId.create(), httpSession);

        return httpSession;
    }
}
