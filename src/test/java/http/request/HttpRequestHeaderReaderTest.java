package http.request;

import java.io.*;

import http.HeaderElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.request.HttpRequestReader.HTTP_METHOD;
import static http.request.HttpRequestReader.REQUEST_URI;
import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderReaderTest {
	private String fileDirectory = "./src/test/resources/";

	@DisplayName("GET요청 시 request header 읽기")
	@Test
	void request_GET() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(fileDirectory + "request_GET.txt"))))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);

			assertThat(httpRequest.getRequestLineElement(HTTP_METHOD)).isEqualTo("GET");
			assertThat(httpRequest.getRequestLineElement(REQUEST_URI)).isEqualTo("/index.html");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.HOST)).isEqualTo("localhost:8080");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.CONNECTION)).isEqualTo("keep-alive");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.ACCEPT)).isEqualTo("*/*");
		}
	}

	@DisplayName("POST요청 시 request header 읽기")
	@Test
	void request_POST() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDirectory + "request_POST.txt")))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);

			assertThat(httpRequest.getRequestLineElement(HTTP_METHOD)).isEqualTo("POST");
			assertThat(httpRequest.getRequestLineElement(REQUEST_URI)).isEqualTo("/user/create");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.HOST)).isEqualTo("localhost:8080");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.CONNECTION)).isEqualTo("keep-alive");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.ACCEPT)).isEqualTo("*/*");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.CONTENT_LENGTH)).isEqualTo("71");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.CONTENT_TYPE)).isEqualTo("application/x-www-form-urlencoded");
			assertThat(httpRequest.getRequestHeaderElement(HeaderElement.ACCEPT)).isEqualTo("*/*");
		}
	}
}