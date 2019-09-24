package webserver;

import http.common.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class SessionHandler {
    private Map<String, HttpSession> session;

    public SessionHandler() {
        session = new HashMap<>();
    }


}
