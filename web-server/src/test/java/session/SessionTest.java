package session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {

    @Test
    @DisplayName("setAttribute")
    void setAttribute() {
        Session session = new Session("abc12345");

        session.setAttribute("id", "chocolate");
        session.setAttribute("password", "1234");
        session.setAttribute("is handsome?", false);

        assertAll(
            () -> assertThat(session.getAttribute("id")).isEqualTo("chocolate"),
            () -> assertThat(session.getAttribute("password")).isEqualTo("1234"),
            () -> assertThat(session.getAttribute("is handsome?")).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("setAttribute - name 이 이미 존재하면 기존 값에 덮어쓰기")
    void setAttribute_IfNameAlreadyExists_Overwrite() {
        Session session = new Session("abc12345");

        session.setAttribute("id", "chocolate");
        session.setAttribute("id", "mint chocolate");

        assertThat(session.getAttribute("id")).isEqualTo("mint chocolate");
    }

    @Test
    @DisplayName("getAttribute")
    void getAttribute() {
        Session session = new Session("abc12345");
        session.setAttribute("flavor", "mint mocha");

        assertThat(session.getAttribute("flavor")).isEqualTo("mint mocha");
    }

    @Test
    @DisplayName("getAttribute - 존재하지 않는 attribute 가져오기 시도시 예외처리")
    void getAttribute_IfNameDoesNotExist_ThrowException() {
        Session session = new Session("abc12345");

        assertThatThrownBy(() -> session.getAttribute("not exist"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getId")
    void getId() {
        Session session = new Session("abc12345");

        assertThat(session.getId()).isEqualTo("abc12345");
    }

    @Test
    @DisplayName("removeAttribute")
    void removeAttribute() {
        Session session = new Session("abc12345");
        session.setAttribute("hi", 1);
        session.setAttribute("hello", 2);

        session.removeAttribute("hi");
        assertThatThrownBy(() -> session.getAttribute("hi"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("removeAttribute - 존재하지 않는 attribute 에 대하여 remove 시도시 예외처리")
    void removeAttribute_IfAttributeIsNotExist_ThrowException() {
        Session session = new Session("abc12345");

        assertThatThrownBy(() -> session.removeAttribute("hi"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("invalidate")
    void invalidate() {
        Session session = new Session("abc12345");
        session.setAttribute("hi", 1);
        session.setAttribute("hello", 2);

        session.invalidate();

        assertThat(session.isInvalid()).isTrue();
    }

    @Test
    @DisplayName("isInvalid")
    void isInvalid() {
        Session session = new Session("abc12345");
        assertThat(session.isInvalid()).isTrue();

        session.setAttribute("hi", true);
        assertThat(session.isInvalid()).isFalse();
    }
}