package webserver.http.session;

import webserver.exception.NotFoundSessionAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
	private Session session;

	@BeforeEach
	void setUp() {
		Object sessionId = "12345678";
		session = new Session(sessionId);
	}

	@DisplayName("세션 아이디 확인")
	@Test
	void getId() {
		assertThat(session.getId()).isEqualTo("12345678");
	}

	@DisplayName("세션 속성 추가")
	@Test
	void setAttribute() {
		String userId = "javajigi";
		session.setAttribute("userId", userId);
		assertThat(session.getAttribute("userId")).isEqualTo(userId);
	}

	@DisplayName("세션에 존재하지 않는 속성 요청 시 예외 발생")
	@Test
	void getAttributeWithException() {
		assertThrows(NotFoundSessionAttribute.class, () -> session.getAttribute("userId"));
	}

	@DisplayName("세션에 존재하는 속성 요청 시 정상 작동")
	@Test
	void getAttribute() {
		String userId = "javajigi";
		session.setAttribute("userId", userId);
		assertDoesNotThrow(() -> session.getAttribute("userId"));
	}

	@DisplayName("세션에 존재하지 않는 속성 삭제 시 예외 발생")
	@Test
	void removeAttributeWithException() {
		assertThrows(NotFoundSessionAttribute.class, () -> session.removeAttribute("userId"));
	}

	@DisplayName("세션에 존재하는 속성 삭제 시 정상 작동")
	@Test
	void removeAttribute() {
		String userId = "javajigi";
		session.setAttribute("userId", userId);
		assertDoesNotThrow(() -> session.removeAttribute("userId"));
	}

	@DisplayName("세션 아이디를 제외한 모든 속성 삭제")
	@Test
	void invalidate() {
		String userId = "javajigi";
		String username = "포비";

		session.setAttribute("userId", userId);
		session.setAttribute("username", username);

		session.invalidate();

		assertDoesNotThrow(() -> session.getAttribute("id"));
		assertThrows(NotFoundSessionAttribute.class, () -> session.getAttribute("userId"));
		assertThrows(NotFoundSessionAttribute.class, () -> session.getAttribute("username"));
	}
}