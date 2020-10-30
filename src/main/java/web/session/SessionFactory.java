package web.session;

import java.util.UUID;

public class SessionFactory {
    public static Session getNewSession() {
        UUID uuid = UUID.randomUUID();
        return new HttpSession(uuid.toString());
    }
}
