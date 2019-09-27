package session;

import java.util.UUID;

public class RandomSessionUtils implements SessionUtils {

    @Override
    public String createId() {
        return UUID.randomUUID().toString();
    }
}
