package model;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserTest {
	@DisplayName("User를 생성한다.")
	@Test
	void ofTest() {
		Map<String, String> userInfo = new HashMap<>();
		userInfo.put("userId", "1");
		userInfo.put("password", "123");
		userInfo.put("name", "doogang");
		userInfo.put("email", "test@test.com");
		User user = User.of(userInfo);

		assertThat(user.getUserId()).isEqualTo("1");
		assertThat(user.getPassword()).isEqualTo("123");
		assertThat(user.getName()).isEqualTo("doogang");
		assertThat(user.getEmail()).isEqualTo("test@test.com");
	}

	@DisplayName("유저의 비밀번호를 검증한다.")
	@ParameterizedTest
	@CsvSource({"123, true", "1, false", "password, false"})
	void validatePasswordTest(String password, boolean expected) {
		Map<String, String> userInfo = new HashMap<>();
		userInfo.put("userId", "1");
		userInfo.put("password", "123");
		User user = User.of(userInfo);

		assertThat(user.validatePassword(password)).isEqualTo(expected);
	}
}
