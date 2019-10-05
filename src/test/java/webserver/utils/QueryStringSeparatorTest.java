package webserver.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringSeparatorTest {
	private String path = "userId=tiber&password=tiber1234&name=tiber&email=tiber@naver.com";

	@DisplayName("쿼리스트링 분리")
	@Test
	void SeparateQueryString() {
		assertThat(saveActualQueryString()).isEqualTo(QueryStringSeparator.separate(path));
	}

	private Map<String, String> saveActualQueryString() {
		Map<String, String> actualQueryString = new HashMap<>();
		actualQueryString.put("userId", "tiber");
		actualQueryString.put("password", "tiber1234");
		actualQueryString.put("name", "tiber");
		actualQueryString.put("email", "tiber@naver.com");
		return actualQueryString;
	}
}