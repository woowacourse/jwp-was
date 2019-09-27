package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HttpSessionTest {
    private HttpSession httpSession;

    @BeforeEach
    @DisplayName("세션 기본 설정")
    void setUp() {
        httpSession = new HttpSession("123");
        httpSession.setAttribute("id", "ddu0422");
        httpSession.setAttribute("name", "mir");
    }

    @Test
    void 세션_확인() {
        assertEquals("ddu0422", httpSession.getAttribute("id"));
    }

    @Test
    void 세션_개별_삭제() {
        httpSession.removeAttribute("id");

        assertNull(httpSession.getAttribute("id"));
        assertEquals("mir", httpSession.getAttribute("name"));
    }

    @Test
    void 세션_초기화() {
        httpSession.invalidate();

        assertNull(httpSession.getAttribute("id"));
        assertNull(httpSession.getAttribute("mir"));
    }

    @Test
    void 세션_아이디() {
        assertEquals(httpSession.getId(), "123");
    }
}
