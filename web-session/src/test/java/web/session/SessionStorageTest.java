package web.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStorageTest {

    @DisplayName("세션 스토리지")
    @Test
    public void saveSession() {
        HttpSession session = SessionStorage.getSession("abcdef");

        assertThat(session.getId()).isEqualTo("abcdef");
    }
}