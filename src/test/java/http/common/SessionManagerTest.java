package http.common;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    @Test
    void 생성된_Session의_ID가_Pool에_등록된_ID와_같은지_테스트() {
        HttpSession httpSession = SessionManager.getSession();

        assertThat(httpSession).isEqualTo(SessionManager.getSession(UUID.fromString(httpSession.getSessionId())));
    }
}
