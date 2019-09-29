package http.session.sessionkeygenerator;

import http.session.Session;

import java.util.Map;
import java.util.UUID;

public class UUIDSessionKeyGenerator implements SessionKeyGenerator {

    @Override
    public String createSessionKey(Map<String, Session> sessions) {
        String uuid = UUID.randomUUID().toString();
        while (sessions.get(uuid) != null) {
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }
}
