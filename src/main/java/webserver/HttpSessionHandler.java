package webserver;

import com.google.common.collect.Maps;
import webserver.common.HttpSession;

import java.util.Map;
import java.util.UUID;

public class HttpSessionHandler {
    private static final Map<String, HttpSession> sessionContext = Maps.newHashMap();

    public static HttpSession getHttpSession(String sessionId) {
        if (sessionContext.containsKey(sessionId)) {
            return sessionContext.get(sessionId);
        }
        return createHttpSession(UUID.randomUUID().toString());
    }

    public static HttpSession createHttpSession(String uuid) {
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
