package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import web.request.RequestLine;

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
	void extractRequestParameters() {
		String path = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
		RequestLine requestLine = RequestLine.of("POST " + path);

		assertThat(RequestParameterUtils.extractRequestParameters(requestLine)).isEqualTo(expect);
	}

	@Test
	void extractBodyParameters() {
		String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

		assertThat(RequestParameterUtils.extractBodyParameters(body)).isEqualTo(expect);

	}
}