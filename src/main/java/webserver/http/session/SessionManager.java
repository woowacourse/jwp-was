package webserver.http.session;

import java.util.UUID;

public class SessionManager {
    public static Session getNewSession() {
        UUID uuid = UUID.randomUUID();
        return new HttpSession(uuid.toString());
    }
}
