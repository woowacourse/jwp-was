package webserver.http.request;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestBodyReaderTest {
	private String fileDirectory = "./src/test/resources/";

	@DisplayName("POST요청 시 request body 읽기")
	@Test
	void request_POST() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDirectory + "request_POST.txt")))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);
			assertThat(httpRequest.getHttpRequestBody()).isEqualTo(saveRequestBody());
		}
	}

	private Map<String, String> saveRequestBody() {
		Map<String, String> actualRequests = new LinkedHashMap<>();
		actualRequests.put("userId", "javajigi");
		actualRequests.put("password", "password");
		actualRequests.put("name", "JaeSung");
		actualRequests.put("email", "javajigi@naver.com");
		return actualRequests;
	}
}