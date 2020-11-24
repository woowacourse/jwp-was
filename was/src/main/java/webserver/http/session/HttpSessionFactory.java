package webserver.http.session;

import java.util.HashMap;
import java.util.UUID;

public class HttpSessionFactory {
    public HttpSession createWithSessionId() {
        String id = UUID.randomUUID().toString();
        return new HttpSession(id, new HashMap<>());
    }
}
