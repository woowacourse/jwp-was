package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionStorageTest {
	@DisplayName("id가 null일 때, session id를 생성한다.")
	@Test
	void getSessionWithNullIdTest() {
		assertThat(SessionStorage.getSession(null).getId()).isNotNull();
	}

	@DisplayName("id에 해당하는 HttpSession이 없는 경우, 생성하여 반환한다.")
	@Test
	void getSessionWithNullSessionTest() {
		HttpSession session = SessionStorage.getSession("123");
		assertThat(session).isEqualTo(SessionStorage.getSession("123"));
	}
}
