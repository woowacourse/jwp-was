package application.service;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.db.DataBase;
import application.model.User;
import application.model.UserLoginException;
import fixtures.HttpRequestGenerator;
import webserver.http.request.HttpRequest;
import webserver.session.SessionStorage;

class UserServiceTest {

	@BeforeEach
	void setUp() {
		DataBase.deleteAll();
		SessionStorage.clear();
	}

	@DisplayName("사용자를 정상적으로 생성한다")
	@Test
	void create() throws IOException {
		HttpRequest request = HttpRequestGenerator.createPostHttpRequest("createUser");

		UserService.create(request);

		User user = DataBase.findUserById("user");
		assertThat(user.getName()).isEqualTo("toney");
	}

	@DisplayName("정상적으로 로그인한다")
	@Test
	void login() throws IOException {
		HttpRequest createRequest = HttpRequestGenerator.createPostHttpRequest("createUser");
		UserService.create(createRequest);

		HttpRequest loginRequest = HttpRequestGenerator.createPostHttpRequest("loginUser");
		String sessionId = UserService.login(loginRequest);

		assertThat(SessionStorage.contain(sessionId)).isTrue();
	}

	@DisplayName("회원가입이 안된 상태로 로그인 시도시 예외가 발생한다")
	@Test
	void loginWithOutSignIn_ThrowException() throws IOException {
		HttpRequest loginRequest = HttpRequestGenerator.createPostHttpRequest("loginUser");
		assertThatThrownBy(() -> UserService.login(loginRequest)).isInstanceOf(UserLoginException.class)
			.hasMessage("해당되는 id의 사용자가 없습니다.");
	}

	@DisplayName("비밀번호가 틀린 상태로 로그인 시도시 예외가 발생한다")
	@Test
	void loginWithWrongPassword_ThrowException() throws IOException {
		HttpRequest createRequest = HttpRequestGenerator.createPostHttpRequest("createUser");
		UserService.create(createRequest);

		HttpRequest loginRequest = HttpRequestGenerator.createPostHttpRequest("loginUserWithWrongPassword");
		assertThatThrownBy(() -> UserService.login(loginRequest)).isInstanceOf(UserLoginException.class)
			.hasMessage("올바르지 않은 비밀번호입니다.");
	}
}