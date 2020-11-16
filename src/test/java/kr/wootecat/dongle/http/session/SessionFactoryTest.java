package kr.wootecat.dongle.http.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionFactoryTest {

    @DisplayName("session 팩토리에서 Session 인스턴스를 생성한다.")
    @Test
    void createSession() {
        String id = "givenSessionId";
        assertThat(SessionFactory.createSession(id))
                .isInstanceOf(Session.class);
    }
}