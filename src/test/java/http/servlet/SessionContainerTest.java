package http.servlet;

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
}
