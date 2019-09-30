package was.http.context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasicSessionHandler implements SessionHandler {
    private static final Map<UUID, Session> MAP = new HashMap<>();

    private BasicSessionHandler() { }

    @Override
    public Session addNewSession(UUID sessionId) {
        Session newSession = new BasicSession();
        MAP.put(sessionId, newSession);
        return newSession;
    }

    @Override
    public Session getSession(UUID sessionId) {
        return MAP.get(sessionId);
    }

    @Override
    public void invalidate(UUID sessionId) {
        MAP.remove(sessionId);
    }

    @Override
    public boolean hasSession(UUID sessionId) {
        return MAP.containsKey(sessionId);
    }

    public static SessionHandler getInstance() {
        return BasicSessionHandler.LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final SessionHandler INSTANCE = new BasicSessionHandler();
    }
}
