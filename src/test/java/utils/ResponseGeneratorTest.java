package utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseGeneratorTest {
	private String filePath = "src/test/resources/responseExample.txt";

	@DisplayName("응답 확인")
	@Test
	void response() {
		Map<String, String> expectHeader = ResponseGenerator.responseHeader(filePath, 1000);
		assertThat(saveResponseHeaderInfo()).isEqualTo(expectHeader);
	}

	private Map<String, String> saveResponseHeaderInfo() {
		Map<String, String> responseHeader = new LinkedHashMap<>();

		responseHeader.put("Http", "HTTP/1.1");
		responseHeader.put("Code", "200");
		responseHeader.put("Description", "OK");
		responseHeader.put("Content-Type", "text/plain;charset=utf-8\r\n");
		responseHeader.put("Content-Length", "1000\r\n");

		return responseHeader;
	}
}