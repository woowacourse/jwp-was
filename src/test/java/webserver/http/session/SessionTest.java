package webserver.http.session;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionTest {

    @Test
    void 세션_아이디_생성() {
        HttpSession httpSession = Session.getInstance().createSession(() -> "123");

        assertEquals(httpSession, Session.getInstance().getHttpSession("123"));
    }
}
