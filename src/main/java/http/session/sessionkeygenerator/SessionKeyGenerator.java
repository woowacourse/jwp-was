package http.session.sessionkeygenerator;

import http.session.Session;

import java.util.Map;

public interface SessionKeyGenerator {
    String createSessionKey(Map<String, Session> sessions);
}
