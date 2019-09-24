package was.http.context;

import was.http.Router;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasicSessionHandler implements SessionHandler {
    private static final Map<UUID, Session> MAP = new HashMap<>();

    private BasicSessionHandler() { }

    @Override
    public void addSession(UUID sessionId) {
        MAP.put(sessionId, new BasicSession());
    }

    @Override
    public Session getSession(UUID sessionId) {
        return MAP.get(sessionId);
    }

    @Override
    public void invalidate(UUID sessionId) {
        MAP.remove(sessionId);
    }

    public static SessionHandler getInstance() {
        return BasicSessionHandler.LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final SessionHandler INSTANCE = new BasicSessionHandler();
    }
}
