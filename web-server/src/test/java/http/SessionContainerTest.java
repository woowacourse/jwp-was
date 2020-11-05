package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SessionContainerTest {

    @DisplayName("세션 컨테이너에서 저장된 세션을 가져오는 기능 테스트")
    @Test
    void getSession() {
        SessionContainer sessionContainer = SessionContainer.getInstance();
        String sessionId = String.valueOf(UUID.randomUUID());
        HttpSession httpSession = new HttpSession(sessionId);

        sessionContainer.put(sessionId, httpSession);

        assertThat(sessionContainer.getSession(sessionId) == httpSession).isTrue();
    }

    @DisplayName("세션 컨테이너에서 저장된 세션을 가져오는 기능 테스트 - 일치하는 세션이 없을 때")
    @Test
    void getSessionWhenNotExist() {
        SessionContainer sessionContainer = SessionContainer.getInstance();
        String sessionId = String.valueOf(UUID.randomUUID());

        assertThat(sessionContainer.getSession(sessionId)).isNull();
    }
}
