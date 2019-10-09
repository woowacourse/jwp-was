package session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionRepository {
    private static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    public synchronized static Session getSession(String jSessionId) {
        return Optional.ofNullable(sessions.get(jSessionId)).orElse(create());
    }

    public static Session create() {
        Session session = Session.of();
        sessions.put(session.getId(), session);
        return session;
    }

}
