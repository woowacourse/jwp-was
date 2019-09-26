package http.session;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private Map<String, String> cookieInformation;

    public Cookie() {
        this.cookieInformation = new HashMap<>();
    }

    public Cookie(Map<String, String> cookieInformation) {
        this.cookieInformation = cookieInformation;
    }

    public String getSessionId() {
        return cookieInformation.get("Session-Id");
    }
}
