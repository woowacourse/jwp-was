package server.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSessions {
    private static final Map<String, HttpSession> httpSessions = new HashMap<>();

    public static HttpSession get(String id) {
        if (Objects.isNull(id)) {
            id = UUID.randomUUID().toString();
        }
        if (!httpSessions.containsKey(id)) {
            httpSessions.put(id, new HttpSession(id));
        }
        return httpSessions.get(id);
    }
}
