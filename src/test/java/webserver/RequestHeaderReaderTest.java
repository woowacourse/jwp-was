package webserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestHeaderReader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderReaderTest {
	private String fileName = "src/test/resources/requestExample.txt";

	@DisplayName("RequestHeader 읽기")
	@Test
	void printRequestHeader() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
			Map<String, String> actualRequests = new HashMap<>();
			saveRequestHeaderInfo(actualRequests);
			Map<String, String> expectRequests = RequestHeaderReader.readRequest(bufferedReader);
			assertThat(actualRequests).isEqualTo(expectRequests);
		}
	}

	private void saveRequestHeaderInfo(Map<String, String> actualRequests) {
		actualRequests.put("Method", "GET");
		actualRequests.put("Path", "/index.html");
		actualRequests.put("Http", "HTTP/1.1");
		actualRequests.put("Host", "localhost:8080");
		actualRequests.put("Connection", "keep-alive");
		actualRequests.put("Accept", "*/*");
	}
}
