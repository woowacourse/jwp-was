package http;

import http.response.SessionRemover;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AbstractSessionTest {
    @Test
    @DisplayName("세션 생성, id 를 가져야함. validated 상태여야 함")
    void createSession() {
        String id = "id";
        Session session = createAbstractSession(id, (receivedId) -> {});

        // IllegalSessionStateException 이 여기서 발생하지 않은 것으로 validated 상태인지도 확인 가능함
        assertThat(session.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("invalidated 인 세션, Session 의 구현 함수들은 모두 IllegalSessionStateException 을 발생시켜야 함")
    void invalidatedSession() {
        String id = "id";
        Session session = createAbstractSession(id, (receivedId) -> {});

        session.invalidate();

        // IllegalSessionStateException 이 여기서 발생하지 않은 것으로 validated 상태인지도 확인 가능함
        assertThrows(IllegalSessionStateException.class, () -> session.getId());
        assertThrows(IllegalSessionStateException.class, () -> session.setAttribute("name", "value"));
        assertThrows(IllegalSessionStateException.class, () -> session.getAttribute("name"));
        assertThrows(IllegalSessionStateException.class, () -> session.removeAttribute("name"));
        assertThrows(IllegalSessionStateException.class, () -> session.invalidate());
    }

    @Test
    @DisplayName("session 을 invalidate 할 경우, SessionRemover.remove() 콜백이 호출되어야 함")
    void invalidateSession_sessionRemoverCallRemove() {
        String id = "id";
        SessionRemover remover = mock(SessionRemover.class);
        Session session = createAbstractSession(id, remover);

        session.invalidate();

        verify(remover).remove(id);
    }

    private Session createAbstractSession(String id, SessionRemover remover) {
        return new AbstractSession(id, remover) {
            @Override
            void _setAttribute(String name, Object value) {

            }

            @Override
            Object _getAttribute(String name) {
                return null;
            }

            @Override
            void _removeAttribute(String name) {

            }
        };
    }
}