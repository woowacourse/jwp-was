package webserver.http.session;

import org.junit.jupiter.api.Test;
import webserver.http.session.exception.InvalidSessionKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpSessionManagerTest {

    @Test
    void 세션_아이디_생성() {
        HttpSession httpSession = HttpSessionManager.getInstance().createSession(() -> "123");

        assertEquals(httpSession, HttpSessionManager.getInstance().getHttpSession("123"));
    }

    @Test
    void 세션카_값이_null_일_경우() {
        assertThrows(InvalidSessionKeyException.class, () -> {
            HttpSessionManager.getInstance().getHttpSession(null);
        });
    }
}
