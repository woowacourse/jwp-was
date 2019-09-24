package session;

import java.util.UUID;

public class HttpSessionFactory {

    public static HttpSession create() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        SessionDB.addSession(httpSession);
        return httpSession;
    }
}
