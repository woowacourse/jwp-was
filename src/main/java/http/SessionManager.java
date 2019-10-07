package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionManager {
    public static final String EMPTY_ID = "";
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private final Map<String, Session> sessions;

    SessionManager(Map<String, Session> sessions) {
        this.sessions = sessions;
    }

    public static SessionManager getInstance() {
        return SessionManager.BillPughSingleton.INSTANCE;
    }

    public Session create() {
        Session session = MapSession.of(UUID.randomUUID().toString(), (id) -> {
            log.debug("remove callback from SessionManager called..!!");
            remove(id);
        });
        sessions.put(session.getId(), session);

        return session;
    }

    public Optional<Session> getSession(String id, boolean canBeCreated) {
        if (sessions.containsKey(id)) {
            return Optional.of(sessions.get(id));
        }

        return Optional.ofNullable(canBeCreated ? create() : null);
    }

    private void remove(String id) {
        sessions.remove(id);
    }

    private static class BillPughSingleton {
        private static final SessionManager INSTANCE = new SessionManager(new HashMap<>());
    }
}
