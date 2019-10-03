package http.common;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionPoolTest {

    @Test
    void 생성된_Session의_ID가_Pool에_등록된_ID와_같은지_테스트() {
        HttpSession httpSession = SessionPool.getSession();

        assertThat(httpSession).isEqualTo(SessionPool.getSession(UUID.fromString(httpSession.getSessionId())));
    }
}
