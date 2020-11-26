package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpSessionsTest {

    @DisplayName("HttpSession 조회 - 저장되어 있지 않은 세션")
    @Test
    void getHttpSession_NotStored() {
        String notStoredId = "Not Stored ID";

        HttpSession httpSession = HttpSessions.getHttpSession(notStoredId);

        Assertions.assertAll(() -> {
            assertThat(httpSession).isNotNull();
            assertThat(httpSession.getId()).isNotNull();
        });
    }

    @DisplayName("HttpSession 조회 - 이미 저장되어 있는 세션")
    @Test
    void getHttpSession_Stored() {
        String id = "Not Stored ID";
        HttpSession httpSession = HttpSessions.getHttpSession(id);
        String storedId = httpSession.getId();

        HttpSession foundHttpSession = HttpSessions.getHttpSession(storedId);

        assertThat(foundHttpSession).isNotNull();
    }

    @DisplayName("HttpSession 생성")
    @Test
    void createHttpSession() {
        HttpSession httpSession = HttpSessions.createHttpSession("test");
        String storedId = httpSession.getId();

        HttpSession foundHttpSession = HttpSessions.getHttpSession(storedId);

        assertThat(foundHttpSession).isNotNull();
    }
}
