package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpSessionTest {
	private final String sessionId = "sessionId";
	private HttpSession session;

	@BeforeEach
	void setUp() {
		session = new HttpSession(sessionId);
	}

	@DisplayName("저장된 session id를 반환한다.")
	@Test
	void getIdTest() {
		assertThat(session.getId()).isEqualTo(sessionId);
	}

	@DisplayName("session의 attribute를 추가한다.")
	@Test
	void setAttributeTest() {
		session.setAttribute("name", "doogang");
		session.setAttribute("logined", true);

		assertThat(session.getAttribute("name")).isEqualTo("doogang");
		assertThat(session.getAttribute("logined")).isEqualTo(true);
	}

	@DisplayName("session의 attribute를 조회한다.")
	@Test
	void getAttributeTest() {
		session.setAttribute("name", "doogang");

		assertThat(session.getAttribute("name")).isEqualTo("doogang");
	}

	@DisplayName("session의 특정 attribute를 삭제한다.")
	@Test
	void removeAttributeTest() {
		session.setAttribute("name", "doogang");
		session.removeAttribute("name");

		assertThat(session.getAttribute("name")).isNull();
	}

	@DisplayName("session의 모든 attribute를 삭제한다.")
	@Test
	void invalidateTest() {
		session.setAttribute("name", "doogang");
		session.setAttribute("logined", true);
		session.invalidate();

		assertThat(session.getAttribute("name")).isNull();
		assertThat(session.getAttribute("logined")).isNull();
	}
}
