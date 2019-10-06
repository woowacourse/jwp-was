package webserver.controller.session;


import exception.invalidSessionIdException;
import model.User;

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

    public String setAttribute(String key , Object value) {
        UUID uuid = UUIDGenerator.generateUUID();
        HttpSession httpSession = new HttpSession(uuid);
        httpSession.setAttributes(key, value);
        this.sessions.put(uuid.toString(),httpSession);
        return uuid.toString();
    }

    private static class LazyHolder {
        private static final HttpSessionManager INSTANCE = new HttpSessionManager();
    }

    public void addSession(HttpSession httpSession) {
        sessions.put(httpSession.getUuid().toString(), httpSession);
    }

    public void removeSession(UUID uuid) {
        if(uuid == null) {
            throw new invalidSessionIdException();
        }
        sessions.remove(uuid.toString());
    }
}
