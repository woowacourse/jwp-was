package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static Map<String, HttpSession> sessionPool = new ConcurrentHashMap<>();

    public static HttpSession getSession(String JSESSIONID) {
        if (JSESSIONID == null) {
            return null;
        }
        return sessionPool.get(JSESSIONID);
    }

    public static void setSession(String JSESSIONID, HttpSession session) {
        sessionPool.put(JSESSIONID, session);
    }
}
