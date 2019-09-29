package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    private HttpSession httpSession;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        httpSession = new HttpSession(uuid);
    }

    @Test
    void 생성시_UUID가_같은지_테스트() {
        assertThat(httpSession.getSessionId()).isEqualTo(uuid.toString());
    }
}
