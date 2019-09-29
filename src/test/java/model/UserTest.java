package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

	@Test
	void 생성테스트() {
		User user = new User("test", "1234", "zino", "zino@zino.zino");

		assertThat(user.getUserId()).isEqualTo("test");
		assertThat(user.getPassword()).isEqualTo("1234");
		assertThat(user.getName()).isEqualTo("zino");
		assertThat(user.getEmail()).isEqualTo("zino@zino.zino");
	}

	@Test
	void 패스워드_일치_테스트() {
		User user = new User("test", "1234", "zino", "zino@zino.zino");
		assertThat(user.checkPassword("1234")).isEqualTo(true);
	}

	@Test
	void 패스워드_불일치_테스트() {
		User user = new User("test", "1234", "zino", "zino@zino.zino");
		assertThat(user.checkPassword("12334")).isEqualTo(false);
	}
}