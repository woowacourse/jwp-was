package http.session;

import java.util.UUID;

public class UUIDGeneretor implements IdGenerator{

    @Override
    public String generateId() {
        return generateRandomUUID().toString();
    }

    private UUID generateRandomUUID() {
        return UUID.randomUUID();
    }
}
