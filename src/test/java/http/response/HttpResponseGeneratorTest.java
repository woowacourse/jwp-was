package http.response;

import java.util.Map;

import http.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseGeneratorTest {
	private String filePath = "./src/test/resources/test.html";

	@DisplayName("200 응답 헤더 확인")
	@Test
	void response200() {
		Map<Header, String> expectHeader = HttpResponseGenerator.response200Header(filePath, 1000);
		assertThat(expectHeader.get(Header.PROTOCOL)).isEqualTo("HTTP/1.1");
		assertThat(expectHeader.get(Header.CODE)).isEqualTo("200");
		assertThat(expectHeader.get(Header.DESCRIPTION)).isEqualTo("OK\r\n");
		assertThat(expectHeader.get(Header.CONTENT_TYPE)).isEqualTo("text/html;charset=utf-8\r\n");
		assertThat(expectHeader.get(Header.CONTENT_LENGTH)).isEqualTo("1000\r\n");
	}

	@DisplayName("302 응답 헤더 확인")
	@Test
	void response302() {
		Map<Header, String> expectHeader = HttpResponseGenerator.response302Header(filePath);
		assertThat(expectHeader.get(Header.PROTOCOL)).isEqualTo("HTTP/1.1");
		assertThat(expectHeader.get(Header.CODE)).isEqualTo("302");
		assertThat(expectHeader.get(Header.DESCRIPTION)).isEqualTo("Found");
		assertThat(expectHeader.get(Header.LOCATION)).isEqualTo(filePath);
	}
}