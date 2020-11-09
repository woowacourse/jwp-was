package jwp.was.webapplicationserver.configure.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UUIDBasedHttpSessionTest {

    @DisplayName("HttpSessionImpl 생성 - 성공")
    @Test
    void Construct_Success() {
        HttpSession httpSession = new UUIDBasedHttpSession();

        assertThat(httpSession.getId()).isNotNull();
        assertThat(httpSession.getId()).isNotEmpty();
        assertThat(httpSession.getId().length()).isGreaterThan(20);
    }
}
