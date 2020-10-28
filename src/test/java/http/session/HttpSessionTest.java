package http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession();
        httpSession.setAttribute("name1", "hiro");
        httpSession.setAttribute("name2", "rebecca");
    }

    @Test
    @DisplayName("세션 생성 테스트")
    void sessionCreateTest() {
        assertThat(httpSession).isNotNull();
    }

    @Test
    @DisplayName("세션의 요소를 조회하는 테스트")
    void getAttributeTest() {
        assertThat(httpSession.getAttribute("name1")).isEqualTo("hiro");
    }

    @Test
    @DisplayName("세션에 요소를 추가하는 테스트")
    void setAttributeTest() {
        httpSession.setAttribute("key", "value");
        assertThat(httpSession.getAttribute("key")).isEqualTo("value");
    }

    @Test
    @DisplayName("세션으로부터 요소를 삭제하는 테스트")
    void deleteAttributeTest() {
        httpSession.setAttribute("key", "value");
        assertThat(httpSession.getAttribute("key")).isEqualTo("value");

        httpSession.removeAttribute("key");
        assertThat(httpSession.getAttribute("key")).isNull();
    }

    @Test
    @DisplayName("세션에 저장되어 있는 모든 값을 삭제하는 테스트")
    void invalidateTest() {
        httpSession.invalidate();

        assertThat(httpSession.getAttribute("name1")).isNull();
        assertThat(httpSession.getAttribute("name2")).isNull();
    }
}