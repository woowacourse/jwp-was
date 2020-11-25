package was.webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import was.webserver.http.session.exception.InvalidHttpSessionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpSessionTest {
    @DisplayName("session의 속성 값 변환 테스트")
    @Test
    void getHttpSessionStringTest() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("Path", "/");
        httpSession.setAttribute("userId", "lavine");

        String expected = httpSession.getHttpSessionString();
        assertAll(
                () -> assertThat(expected).contains("Path=/"),
                () -> assertThat(expected).containsPattern("jsessionid=[a-z0-9\\-]+; "),
                () -> assertThat(expected).doesNotContain("userId")
        );
    }

    @DisplayName("유효한 session인지 확인")
    @Test
    void isValidSessionTest() {
        HttpSession validSession = new HttpSession();
        HttpSession invalidSession = new HttpSession();
        invalidSession.invalidate();

        assertAll(
                () -> assertThat(validSession.isValidSession()).isTrue(),
                () -> assertThat(invalidSession.isValidSession()).isFalse()
        );
    }

    @DisplayName("유효하지 않은 session에 대해 메서드 호출 시 예외 반환")
    @Test
    void callMethodWithInvalidHttpSessionTest() {
        HttpSession invalidSession = new HttpSession();
        invalidSession.invalidate();

        assertAll(
                () -> assertThatThrownBy(() -> invalidSession.setAttribute("userId", "userId"))
                        .isInstanceOf(InvalidHttpSessionException.class),
                () -> assertThatThrownBy(() -> invalidSession.getAttribute("userId"))
                        .isInstanceOf(InvalidHttpSessionException.class),
                () -> assertThatThrownBy(() -> invalidSession.removeAttribute("userId"))
                        .isInstanceOf(InvalidHttpSessionException.class)
        );
    }
}
