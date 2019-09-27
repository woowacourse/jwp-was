package dev.luffy.http.session;

import java.util.UUID;

public class SessionIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
