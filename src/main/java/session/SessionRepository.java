package session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionRepository {
    private static Map<String, Session> sessions = new HashMap<>();

    public static Session getSession(String jSessionId) {
        return Optional.ofNullable(sessions.get(jSessionId)).orElse(create());
    }

    public static Session create() {
        Session session = Session.of();
        sessions.put(session.getId(), session);
        return session;
    }

}
