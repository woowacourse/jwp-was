package webserver.process;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.http.HeaderName;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;

class HttpProcessorTest {

	@DisplayName("잘못된 Method가 들어왔을 경우 - IllegalArgumentException 발생")
	@Test
	void processException() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "잘못된 METHOD");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody("");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		assertThatThrownBy(() -> HttpProcessor.process(httpRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("GET - index.html 요청")
	@Test
	void proceeIndexHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		headers.put(HeaderName.RequestUrl.name(), "/index.html");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody("");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		byte[] actual = HttpProcessor.process(httpRequest);
		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");

		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("GET - /user/form.html 요청")
	@Test
	void processFormHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		headers.put(HeaderName.RequestUrl.name(), "/user/form.html");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody("");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		byte[] actual = HttpProcessor.process(httpRequest);
		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");

		assertThat(actual).isEqualTo(expected);
	}
}