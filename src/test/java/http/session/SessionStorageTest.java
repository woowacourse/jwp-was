package http.session;

import http.response.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionStorageTest {
    private Cookie cookie;
    private SessionStorage sessionStorage = SessionStorage.getInstance();;

    @BeforeEach
    public void setUp() {
        Session session = sessionStorage.getSession("");
        cookie = Cookie.builder().name("JSESSIONID").value(session.getSessionId()).build();
        session.setAttribute("logined", "true");
    }

    @Test
    public void loginSession() {
        Session session = SessionStorage.getInstance().getSession(cookie.getValue());
        assertThat(session.getAttribute("logined")).isEqualTo("true");
    }
}
