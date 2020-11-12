package http.session;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.InvalidSessionException;

public class HttpSessionManager implements SessionManager {

    public static final Logger logger = LoggerFactory.getLogger(HttpSessionManager.class);
    private final SessionIdGenerateStrategy strategy;
    private Map<String, HttpSession> sessions = new HashMap<>();

    public HttpSessionManager(SessionIdGenerateStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public HttpSession generateSession() {
        return generateNewSession();
    }

    private HttpSession generateNewSession() {
        String id = generateRandomSessionId();
        HttpSession httpSession = new HttpSession(id);
        logger.info("GENERATE SESSION : ", httpSession);
        sessions.put(id, httpSession);
        return httpSession;
    }

    private String generateRandomSessionId() {
        String id;
        do {
            id = strategy.generate();
        } while (containSessionId(id));
        return id;
    }

    private boolean containSessionId(String id) {
        return sessions.containsKey(id);
    }

    @Override
    public HttpSession getSession(String id) {
        if (containSessionId(id)) {
            return sessions.get(id);
        }
        throw new InvalidSessionException();
    }

    @Override
    public boolean validate(String id) {
        return sessions.containsKey(id);
    }
}
