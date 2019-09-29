package http.session.sessionkeygenerator;

import http.session.Session;

import java.util.Map;

public class CustomMadeSessionKeyGenerator implements SessionKeyGenerator {

    @Override
    public String createSessionKey(Map<String, Session> sessions) {
        return "123456";
    }
}
