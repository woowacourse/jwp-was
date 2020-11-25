package webserver.session;

import java.util.UUID;

public class HttpSessionFactory {

    public HttpSession create() {
        UUID uuid = UUID.randomUUID();
        return new HttpSession(uuid.toString());
    }
}
