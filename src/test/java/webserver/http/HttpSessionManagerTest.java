package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {
    private HttpSessionManager manager = HttpSessionManager.getInstance();

    @Test
    void session_생성후_조회_확인() {
        // given
        final HttpSession session = manager.getSession();

        // when & then
        assertThat(session).isEqualTo(manager.getSession(session.getId()));
    }

    @Test
    void 싱글톤_확인() {
        assertThat(manager == HttpSessionManager.getInstance()).isTrue();
    }
}