package http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpSessionTest {
    @DisplayName("http session의 toString 테스트")
    @Test
    void toStringTest() {
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("path", "/");
        httpSession.setAttribute("expires", LocalDateTime.of(2020, 11, 30, 10, 0));
        httpSession.setAttribute("HttpOnly", true);
        httpSession.setAttribute("secure", false);
        httpSession.setAttribute("userId", "yuan");

        String result = httpSession.toString();
        assertAll(
            () -> assertThat(result).contains("path=/"),
            () -> assertThat(result).contains("expires=2020-11-30T10:00"),
            () -> assertThat(result).contains("HttpOnly"),
            () -> assertThat(result).containsPattern("jsessionid=[a-z0-9\\-]+; "),
            () -> assertThat(result).doesNotContain("userId")
        );
    }
}