package webserver.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

	@DisplayName("id와 함께 생성된다.")
	@Test
	void create() {
		HttpSession httpSession = new HttpSession();
		assertThat(httpSession.getId()).isNotNull();
	}

	@DisplayName("Attributes에 저장한다.")
	@Test
	void setAttributes() {
		HttpSession httpSession = new HttpSession();
		httpSession.setAttributes("key", "value");

		assertThat(httpSession.getAttribute("key")).isEqualTo("value");
	}

	@DisplayName("Attributes에서 key를 이용하여 value를 제거한다.")
	@Test
	void removeAttribute() {
		HttpSession httpSession = new HttpSession();
		httpSession.setAttributes("key", "value");

		httpSession.removeAttribute("key");

		assertThat(httpSession.getAttribute("key")).isNull();
	}

	@DisplayName("Attributes의 모든 key와 value 쌍을 제거한다.")
	@Test
	void invalidate() {
		HttpSession httpSession = new HttpSession();
		httpSession.setAttributes("key", "value");
		httpSession.setAttributes("other-key", "other-value");

		httpSession.invalidate();

		assertAll(
			() -> assertThat(httpSession.getAttribute("key")).isNull(),
			() -> assertThat(httpSession.getAttribute("other-key")).isNull()
		);
	}
}