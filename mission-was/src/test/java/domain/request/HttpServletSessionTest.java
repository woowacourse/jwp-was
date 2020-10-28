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
        HttpSession httpSession = new HttpSession("1");
        httpSession.setAttribute("hongbin", "gangster");
        assertThat(httpSession.getAttribute()).containsEntry("hongbin", "gangster");
    }

    @DisplayName("세션에 attribute가 잘 담겼는지 확인한다.")
    @Test
    void getAttribute() {
        HttpSession httpSession = new HttpSession("1");
        httpSession.setAttribute("hongbin", "gangster");
        Object attributeValue = httpSession.getAttribute("hongbin");
        assertThat(attributeValue).isEqualTo("gangster");
    }

    @DisplayName("세션에 attribute를 삭제한다.")
    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession("1");
        httpSession.setAttribute("hongbin", "gangster");
        httpSession.removeAttribute("hongbin");
        assertAll(
            () -> assertThat(httpSession.getAttribute()).doesNotContainKey("hongbin"),
            () -> assertThat(httpSession.getAttribute()).doesNotContainValue("gangster")
        );
    }

    @DisplayName("세션의 모든 attribute를 삭제한다.")
    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSession("1");
        httpSession.setAttribute("hongbin", "gangster");
        httpSession.invalidate();
        assertThat(httpSession.getAttribute()).hasSize(0);
    }

    @DisplayName("세션에 할당되어 있는 고유한 세션 아이디를 반환한다.")
    @Test
    void getId() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid.toString());
        assertThat(httpSession.getId()).isEqualTo(uuid.toString());
    }
}
