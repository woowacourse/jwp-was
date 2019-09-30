package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpSessionStoreTest {

    @Test
    void putSession() {
        HttpSession httpSession1 = HttpSessionStore.getSession("");
        String sessionId = httpSession1.getId();

        HttpSession httpSession2 = HttpSessionStore.getSession(sessionId);

        assertThat(httpSession1).isEqualTo(httpSession2);
    }

    @Test
    void getSession() {
    }
}