package webserver.controller.session;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private final Map<String, HttpSession> sessions;

    private HttpSessionManager() {
        sessions = new HashMap<>();
    }

    public static HttpSessionManager getInstance() {
        return HttpSessionManager.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpSessionManager INSTANCE = new HttpSessionManager();
    }

    public void addSession(UUID uuid, HttpSession httpSession) {
        sessions.put(uuid.toString(), httpSession);
    }

    public void removeSession(UUID uuid) {
        sessions.remove(uuid.toString());
    }
}
