package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * request.getSession() 호출
 * Cookie에 sessionId 확인
 * 인스턴스가 있으면 session 객체 리턴, 없으면 빈 세션 객체 리턴 해준다.
 * SetAttribute 해주면 객체를 생성한다. (빈 세션일 경우)
 * 생성할 떄 id는 UUID.randomUUID();
 * Map<SessionId, Session> 에 저장해준다.
 * <p>
 * - getId()
 * - setAttribute(String name, Object value)
 * - getAttribute(String name)
 * - removeAttribute(String name)
 * - invalidate()
 **/

class HttpSessionTest {

    @Test
    void 추가_확인() {
        // given
        final HttpSession httpSession = new HttpSession();
        final String name = "name";
        final String value = "value";

        // when
        httpSession.setAttribute(name, value);

        // then
        assertThat(value).isEqualTo(httpSession.getAttribute(name));
    }

    @Test
    void 모든_데이터_삭제() {
        // given
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("test1", "test1");
        httpSession.setAttribute("test2", "test1");

        // when
        httpSession.invalidate();

        // then
        assertThat(0).isEqualTo(httpSession.size());
    }

    @Test
    void 특정_데이터_삭제() {
        // given
        final HttpSession httpSession = new HttpSession();
        final String name = "name";
        final String value = "value";
        httpSession.setAttribute(name, value);

        // when
        httpSession.removeAttribute(name);

        // then
        assertThat(httpSession.getAttribute(name)).isNull();
    }
}

