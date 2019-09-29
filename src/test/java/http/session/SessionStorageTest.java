package http.session;

import http.cookie.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionStorageTest {
    private Cookie cookie;
    private SessionStorage sessionStorage = SessionStorage.getInstance();;

    @BeforeEach
    public void setUp() {
        Session session = sessionStorage.createSession();
        cookie = Cookie.builder().name(SessionStorage.JSESSIONID).value(session.getSessionId()).build();
        session.setAttribute("logined", "true");
    }

    @Test
    public void loginSession() {
        Session session = SessionStorage.getInstance().getSession(cookie.getValue());
        assertThat(session.getAttribute("logined")).isEqualTo("true");
    }
}
