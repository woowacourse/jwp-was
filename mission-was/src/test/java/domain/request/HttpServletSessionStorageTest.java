package domain.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import servlet.HttpSession;

class HttpServletSessionStorageTest {

    @DisplayName("새로운 세션을 생성하고, 세션을 반환한다.")
    @Test
    void createSession() {
        HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();
        HttpSession session = httpSessionStorage.getSession("");
        HttpSession actual = httpSessionStorage.getSession(session.getId());
        assertThat(actual).isNotNull();
    }
}
