package http.common;

import http.exception.SessionExpireException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void 세션에_등록된_시간이_먄료된_경우_세션_만료_예외처리() {
        httpSession = new HttpSession(uuid, LocalDateTime.now());

        assertThrows(SessionExpireException.class, () -> httpSession.getAttribute("anyone"));
    }
}
