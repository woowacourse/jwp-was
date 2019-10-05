package application.service;

import java.util.HashMap;
import java.util.Map;

import application.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
	@Test
	void saveUser() {
		User actualUser = new User("javajigi", "password", "JaeSung", "javajigi@naver.com");
		User expectUser = UserService.saveUser(getUserInfo());
		assertThat(actualUser).isEqualTo(expectUser);
	}

	public Map<String, String> getUserInfo() {
		Map<String, String> userInfo = new HashMap<>();
		userInfo.put("userId", "javajigi");
		userInfo.put("password", "password");
		userInfo.put("name", "JaeSung");
		userInfo.put("email", "javajigi@naver.com");
		return userInfo;
	}
}