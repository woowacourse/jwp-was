package webserver;

import com.google.common.collect.Maps;
import webserver.common.HttpSession;

import java.util.Map;
import java.util.UUID;

public class HttpSessionHandler {
    private static final Map<String, HttpSession> sessionContext = Maps.newHashMap();

    public static HttpSession getHttpSession(String sessionId) {
        return sessionContext.get(sessionId);
    }

    public static HttpSession createHttpSession() {
        String uuid = UUID.randomUUID().toString();
        sessionContext.put(uuid, HttpSession.of(uuid));
        return sessionContext.get(uuid);
    }

    public static boolean hasHttpSession(String sessionId) {
        return sessionContext.containsKey(sessionId);
    }

    public static void remove(String sessionId) {
        sessionContext.remove(sessionId);
    }
}
