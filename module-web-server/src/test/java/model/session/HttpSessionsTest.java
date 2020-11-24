package model.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionsTest {

    private static final String SESSION_ID = "1";

    @Test
    @DisplayName("Session 확인 - 기존에 존재하지 않는 세션일 경우")
    void getHttpSession() {
        assertThat(HttpSessions.getHttpSession(SESSION_ID))
            .isInstanceOf(HttpSession.class);
    }

    @Test
    @DisplayName("Session 확인 - 기존에 존재했던 세션일 경우")
    void getHttpSession_AlreadyExistSession() {
        HttpSessions.getHttpSession(SESSION_ID);
        assertThat(HttpSessions.getHttpSession(SESSION_ID))
            .isInstanceOf(HttpSession.class);
    }
}