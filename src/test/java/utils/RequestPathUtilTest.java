package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class RequestPathUtilTest {

	@Test
	public void getTemplatePath() {
		String request = "/index.html";

		assertThat(RequestPathUtil.extractFilePath(request)).isEqualTo("./templates/index.html");
	}

	@Test
	public void getStaticPath() {
		String request = "/index.css";

		assertThat(RequestPathUtil.extractFilePath(request)).isEqualTo("./static/index.css");
	}

	@Test
	public void getRequestParameters() {
		String request = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

		Map<String, String> expect = new HashMap<>();
		expect.put("userId", "javajigi");
		expect.put("password", "password");
		expect.put("name", "박재성");
		expect.put("email", "javajigi@slipp.net");

		assertThat(RequestPathUtil.extractParameters(request)).isEqualTo(expect);
	}
}