package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginStatusTest {
	@DisplayName("로그인 상태에 따른 적절한 LoginStatus를 반환한다.")
	@ParameterizedTest
	@CsvSource({"true, SUCCESS", "false, FAIL"})
	void ofTest(boolean validated, LoginStatus loginStatus) {
		assertThat(LoginStatus.of(validated)).isEqualTo(loginStatus);
	}
}
