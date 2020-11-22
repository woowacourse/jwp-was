package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpSessionStorageTest {
    @DisplayName("session 생성 테스트")
    @Test
    void createTest() {
        HttpSession httpSession = HttpSessionStorage.create("lavine");
        String expected = httpSession.getHttpSessionString();

        assertAll(
                () -> assertThat(expected).contains("Path=/"),
                () -> assertThat(expected).containsPattern("jsessionid=[a-z0-9\\-]+; ")
        );
    }

    @DisplayName("유효한 session인지 확인")
    @Test
    void isValidSessionTest() {
        HttpSession httpSession = HttpSessionStorage.create("lavine");

        assertThat(HttpSessionStorage.isValidSession(Collections.singletonList(
                "jsessionid=" + httpSession.getId().toString()))).isTrue();
    }
}
