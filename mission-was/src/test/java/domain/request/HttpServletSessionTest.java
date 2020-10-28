package domain.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpServletSessionTest {

    @DisplayName("세션에 attribute를 설정한다.")
    @Test
    void setAttribute() {
        HttpServletSession httpSession = new HttpServletSession("1");
        httpSession.setAttribute("hongbin", "gangster");
        assertThat(httpSession.getAttribute()).containsEntry("hongbin", "gangster");
    }

    @DisplayName("세션에 attribute가 잘 담겼는지 확인한다.")
    @Test
    void getAttribute() {
        HttpServletSession HttpServletSession = new HttpServletSession("1");
        HttpServletSession.setAttribute("hongbin", "gangster");
        Object attributeValue = HttpServletSession.getAttribute("hongbin");
        assertThat(attributeValue).isEqualTo("gangster");
    }

    @DisplayName("세션에 attribute를 삭제한다.")
    @Test
    void removeAttribute() {
        HttpServletSession HttpServletSession = new HttpServletSession("1");
        HttpServletSession.setAttribute("hongbin", "gangster");
        HttpServletSession.removeAttribute("hongbin");
        assertAll(
            () -> assertThat(HttpServletSession.getAttribute()).doesNotContainKey("hongbin"),
            () -> assertThat(HttpServletSession.getAttribute()).doesNotContainValue("gangster")
        );
    }

    @DisplayName("세션의 모든 attribute를 삭제한다.")
    @Test
    void invalidate() {
        HttpServletSession HttpServletSession = new HttpServletSession("1");
        HttpServletSession.setAttribute("hongbin", "gangster");
        HttpServletSession.invalidate();
        assertThat(HttpServletSession.getAttribute()).hasSize(0);
    }

    @DisplayName("세션에 할당되어 있는 고유한 세션 아이디를 반환한다.")
    @Test
    void getId() {
        UUID uuid = UUID.randomUUID();
        HttpServletSession HttpServletSession = new HttpServletSession(uuid.toString());
        assertThat(HttpServletSession.getId()).isEqualTo(uuid.toString());
    }
}
