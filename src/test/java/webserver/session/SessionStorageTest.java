package webserver.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStorageTest {

	@DisplayName("HttpSession을 생성하여 Storage에 저장한다.")
	@Test
	void create() {
		String sessionId = SessionStorage.create("userId", "toneytoneytoney");
		assertThat(SessionStorage.contain(sessionId)).isTrue();
	}

	@DisplayName("세션의 login 속성을 true로 바꾼다.")
	@Test
	void loginBy() {
		String sessionId = SessionStorage.create("userId", "toneytoneytoney");
		SessionStorage.loginBy(sessionId);

		assertThat((boolean)SessionStorage.findBy(sessionId).getAttribute("logined")).isTrue();
	}

	@DisplayName("SessionStorage의 내용을 비운다.")
	@Test
	void clear() {
		String sessionId = SessionStorage.create("userId", "toneytoneytoney");
		SessionStorage.clear();

		assertThat(SessionStorage.findBy(sessionId)).isNull();
	}
}