package webserver.controller.session;


import exception.invalidSessionIdException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private final Map<String, HttpSession> sessions;

    private HttpSessionManager() {
        sessions = new ConcurrentHashMap<>();
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
        if(uuid == null) {
            throw new invalidSessionIdException();
        }
        sessions.remove(uuid.toString());
    }
}
