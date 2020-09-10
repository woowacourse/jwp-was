package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpMainHeaderTest {

	@Test
	@DisplayName("생성자 테스트")
	void init() {
		assertThat(new HttpMainHeader(HttpMethod.GET, "/")).isInstanceOf(HttpMainHeader.class);
	}
}