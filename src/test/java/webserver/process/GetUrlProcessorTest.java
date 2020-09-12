package webserver.process;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;
import webserver.http.HeaderName;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.process.get.GetUrlProcessor;

class GetUrlProcessorTest {

	@DisplayName("올바르지 않은 GET Method Url 요청")
	@Test
	void apply() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		headers.put(HeaderName.RequestUrl.name(), "/올바르지않은url");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody("");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		assertThatThrownBy(() -> new GetUrlProcessor().apply(httpRequest))
			.isInstanceOf(NotExistUrlException.class);
	}

	@DisplayName("/index.html 요청 처리")
	@Test
	void applyByIndexHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		headers.put(HeaderName.RequestUrl.name(), "/index.html");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody("");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		byte[] actual = new GetUrlProcessor().apply(httpRequest);

		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");

		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("/user/form.html 요청 처리")
	@Test
	void applyByUserFormHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		headers.put(HeaderName.RequestUrl.name(), "/user/form.html");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody("");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		byte[] actual = new GetUrlProcessor().apply(httpRequest);

		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");

		assertThat(actual).isEqualTo(expected);
	}
}