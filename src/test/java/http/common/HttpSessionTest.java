package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    private HttpSession httpSession;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        httpSession = new HttpSession(uuid, LocalDateTime.now().plusMinutes(SessionManager.DEFAULT_EXPIRE_TIME));
    }

    @Test
    void 생성시_UUID가_같은지_테스트() {
        assertThat(httpSession.getSessionId()).isEqualTo(uuid.toString());
    }
}
