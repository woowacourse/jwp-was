package http.session;

import http.exceptions.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager implements SessionManager {
    private static final Logger log = LoggerFactory.getLogger(HttpSessionManager.class);
    private final IdGenerateStrategy idGenerateStrategy;
    private Map<String, HttpSession> sessions = new HashMap<>();

    public HttpSessionManager(IdGenerateStrategy idGenerateStrategy) {
        this.idGenerateStrategy = idGenerateStrategy;
    }

    @Override
    public HttpSession getSession(String id) {
        if (hasSessionId(id)) {
            HttpSession session = sessions.get(id);
            log.info("SESSION REQUESTED, RETURN {}", session);
            return session;
        }
        throw new InvalidSessionException();
    }

    @Override
    public HttpSession newSession() {
        return generate();
    }

    @Override
    public boolean validate(String id) {
        return sessions.containsKey(id);
    }

    private HttpSession generate() {
        String id = generateId();
        log.info("SESSION GENERATED {}", id);
        HttpSession session = new HttpSession(id);
        sessions.put(id, session);
        return session;
    }

    private String generateId() {
        String id;
        do {
            id = idGenerateStrategy.generate();
        } while (hasSessionId(id));
        return id;
    }

    private boolean hasSessionId(String id) {
        return sessions.keySet().contains(id);
    }
}
