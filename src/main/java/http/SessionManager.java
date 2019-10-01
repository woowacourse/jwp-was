package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);

    public static final String EMPTY_ID = "";

    private final Map<String, Session> sessions;

    SessionManager(Map<String, Session> sessions) {
        this.sessions = sessions;
    }

    private static class BillPughSingleton {
        private static final SessionManager INSTANCE = new SessionManager(new HashMap<>());
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

    // id 가 없을때 생성을 한다면.. 주어진 id 로 만들어야 할까?
    public Optional<Session> getSession(String id, boolean canBeCreated) {
        Session session = Optional.ofNullable(sessions.get(id))
                .orElse(canBeCreated ? create() : null);

        return Optional.ofNullable(session);
    }

    private void remove(String id) {
        sessions.remove(id);
    }
}
