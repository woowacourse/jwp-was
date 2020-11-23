package http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionStorageTest {
    public static final String COOKIE = "jsessionid=%s; path=/; expires=Mon, 30 Nov 2020 05:12:27 GMT; secure; HttpOnly; SameSite=Lax";

    @Test
    void getSessionFrom() {
        HttpSession httpSession = HttpSessionStorage.generate("yuan");

        String cookie = String.format(COOKIE, httpSession.getId().toString());
        boolean result = HttpSessionStorage.isValidSession(cookie);

        assertThat(result).isTrue();
    }

    @Test
    void getSessionFromNotSaved() {
        String cookie = String.format(COOKIE, "weird_session_id");

        boolean result = HttpSessionStorage.isValidSession(cookie);
        assertThat(result).isFalse();
    }
}
