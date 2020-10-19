package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSessions {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getHttpSession(String id) {
        HttpSession session = sessions.get(id);

        if (Objects.isNull(session)) {
            String randomUUID = UUID.randomUUID().toString();
            return createHttpSession(randomUUID);
        }
        return session;
    }

    public static HttpSession createHttpSession(String randomUUID) {
        HttpSession newSession = new HttpSession(randomUUID);
        sessions.put(randomUUID, newSession);

        return newSession;
    }
}
