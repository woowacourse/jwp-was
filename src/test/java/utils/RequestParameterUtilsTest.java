package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestParameterUtilsTest {
	private Map<String, String> expect = new HashMap<>();

	@BeforeEach
	void setUp() {
		expect.put("userId", "javajigi");
		expect.put("password", "password");
		expect.put("name", "박재성");
		expect.put("email", "javajigi@slipp.net");
	}

	@Test
	void extractParametersIfGetMethod() {
		String path = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

		assertThat(RequestParameterUtils.extractParametersIfGetMethod(path)).isEqualTo(expect);
	}

	@Test
	void extractParametersIfPostMethod() {
		String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

		assertThat(RequestParameterUtils.extractParametersIfPostMethod(body)).isEqualTo(expect);

	}
}