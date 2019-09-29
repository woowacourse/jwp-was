package http.session;

import java.util.UUID;

public class UUIDSessionKeyStrategy implements SessionKeyGenerator {
    @Override
    public String create() {
        return UUID.randomUUID().toString();
    }
}
