package http.response;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseGeneratorTest {
	private String filePath = "./src/test/resources/test.html";

	@DisplayName("200 응답 헤더 확인")
	@Test
	void response200() {
		Map<String, String> expectHeader = HttpResponseGenerator.response200Header(filePath, 1000);
		assertThat(expectHeader.get("Http")).isEqualTo("HTTP/1.1");
		assertThat(expectHeader.get("Code")).isEqualTo("200");
		assertThat(expectHeader.get("Description")).isEqualTo("OK");
		assertThat(expectHeader.get("Content-Type")).isEqualTo("text/html;charset=utf-8\r\n");
		assertThat(expectHeader.get("Content-Length")).isEqualTo("1000\r\n");
	}

	@DisplayName("302 응답 헤더 확인")
	@Test
	void response302() {
		Map<String, String> expectHeader = HttpResponseGenerator.response302Header(filePath);
		assertThat(expectHeader.get("Http")).isEqualTo("HTTP/1.1");
		assertThat(expectHeader.get("Code")).isEqualTo("302");
		assertThat(expectHeader.get("Description")).isEqualTo("Found");
		assertThat(expectHeader.get("Location")).isEqualTo(filePath);
	}
}