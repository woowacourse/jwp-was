package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExtractUtilsTest {
	private static final String BODY = "userId=a&password=1&name=asd&email=test%40test.com";

	@DisplayName("URL의 Param을 분리")
	@Test
	void extractUserInfoTest() throws UnsupportedEncodingException {
		Map<String, String> userInfo = ExtractUtils.extractUserInfo(BODY);

		assertAll(
			() -> assertThat(userInfo.get("userId")).isEqualTo("a"),
			() -> assertThat(userInfo.get("password")).isEqualTo("1"),
			() -> assertThat(userInfo.get("name")).isEqualTo("asd"),
			() -> assertThat(userInfo.get("email")).isEqualTo("test@test.com")
		);
	}
}
