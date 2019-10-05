package webserver.http.request;

import java.io.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestReaderTest {
	private String fileDirectory = "./src/test/resources/";

	@DisplayName("Get 요청 시 requeset body가 null인지 확인")
	@Test
	void request_Get() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(fileDirectory + "request_GET.txt"))))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);
			assertThrows(NullPointerException.class, httpRequest::getHttpRequestBody);
		}
	}

	@DisplayName("Post 요청 시 requeset body가 null이 아닌지 확인")
	@Test
	void request_Post() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(fileDirectory + "request_POST.txt"))))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);
			assertThat(httpRequest.getHttpRequestBody()).isNotNull();
		}
	}
}