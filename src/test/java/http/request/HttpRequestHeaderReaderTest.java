package http.request;

import java.io.*;

import http.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderReaderTest {
	private String fileDirectory = "./src/test/resources/";

	@DisplayName("GET요청 시 request header 읽기")
	@Test
	void request_GET() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(fileDirectory + "request_GET.txt"))))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);

			assertThat(httpRequest.getRequestHeaderElement(Header.METHOD)).isEqualTo("GET");
			assertThat(httpRequest.getRequestHeaderElement(Header.PATH)).isEqualTo("/index.html");
			assertThat(httpRequest.getRequestHeaderElement(Header.PROTOCOL)).isEqualTo("HTTP/1.1");
			assertThat(httpRequest.getRequestHeaderElement(Header.HOST)).isEqualTo("localhost:8080");
			assertThat(httpRequest.getRequestHeaderElement(Header.CONNECTION)).isEqualTo("keep-alive");
			assertThat(httpRequest.getRequestHeaderElement(Header.ACCEPT)).isEqualTo("*/*");
		}
	}

	@DisplayName("POST요청 시 request header 읽기")
	@Test
	void request_POST() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDirectory + "request_POST.txt")))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);

			assertThat(httpRequest.getRequestHeaderElement(Header.METHOD)).isEqualTo("POST");
			assertThat(httpRequest.getRequestHeaderElement(Header.PATH)).isEqualTo("/user/create");
			assertThat(httpRequest.getRequestHeaderElement(Header.PROTOCOL)).isEqualTo("HTTP/1.1");
			assertThat(httpRequest.getRequestHeaderElement(Header.HOST)).isEqualTo("localhost:8080");
			assertThat(httpRequest.getRequestHeaderElement(Header.CONNECTION)).isEqualTo("keep-alive");
			assertThat(httpRequest.getRequestHeaderElement(Header.ACCEPT)).isEqualTo("*/*");
			assertThat(httpRequest.getRequestHeaderElement(Header.CONTENT_LENGTH)).isEqualTo("46");
			assertThat(httpRequest.getRequestHeaderElement(Header.CONTENT_TYPE)).isEqualTo("application/x-www-form-urlencoded");
			assertThat(httpRequest.getRequestHeaderElement(Header.ACCEPT)).isEqualTo("*/*");
		}
	}
}