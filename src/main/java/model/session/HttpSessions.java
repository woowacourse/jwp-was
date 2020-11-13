package model.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSessions {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getHttpSession(String id) {
        HttpSession session = sessions.get(id);

        if (Objects.isNull(session)) {
            return makeNewHttpSession(id);
        }
        return session;
    }

    private static HttpSession makeNewHttpSession(String id) {
        HttpSession newSession = new HttpSession(id);
        sessions.put(id, newSession);

        return newSession;
    }
}