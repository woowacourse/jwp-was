package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Map<String, HttpSession> sessionPool = new ConcurrentHashMap<>();

    public HttpSessionManager() {
    }

    public HttpSession getSession(String JSESSIONID) {
        if (StringUtils.isBlank(JSESSIONID)) {
            return null;
        }
        return sessionPool.get(JSESSIONID);
    }

    public void setSession(String JSESSIONID, HttpSession session) {
        sessionPool.put(JSESSIONID, session);
    }
}
