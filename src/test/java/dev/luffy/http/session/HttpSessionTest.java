package dev.luffy.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpSessionTest {

    private static final String CUSTOM_SESSION_ID = "3dad4e68-a18f-44ec-bd24-c713fcb40137";
    private static final String LOGINED = "logined";
    private static final String FALSE = "false";

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession(CUSTOM_SESSION_ID);
    }

    @DisplayName("getId 메서드가 성공적으로 작동한다.")
    @Test
    void getId() {
        assertEquals(httpSession.getId(), CUSTOM_SESSION_ID);
    }

    @DisplayName("getAttribute 메서드가 성공적으로 작동한다.")
    @Test
    void getAttributeSuccess() {
        httpSession.setAttribute(LOGINED, FALSE);
        assertEquals(httpSession.getAttribute(LOGINED), FALSE);
    }

    @DisplayName("removeAttribute 메서드가 성공적으로 작동한다.")
    @Test
    void removeAttributed() {
        httpSession.setAttribute(LOGINED, FALSE);
        httpSession.removeAttribute(LOGINED);
        assertNull(httpSession.getAttribute(LOGINED));
    }
}