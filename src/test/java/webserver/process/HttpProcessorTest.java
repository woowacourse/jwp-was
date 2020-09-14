package webserver.process;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static webserver.http.response.HttpResponseHeaderName.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeaderName;
import webserver.http.request.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponse;
import webserver.http.response.StatusCode;

class HttpProcessorTest {

	@DisplayName("잘못된 Method가 들어왔을 경우 - IllegalArgumentException 발생")
	@Test
	void processException() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "잘못된 METHOD");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

		assertThatThrownBy(() -> HttpProcessor.process(httpRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("GET - index.html 요청")
	@Test
	void processIndexHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "GET");
		headers.put(HttpRequestHeaderName.RequestUrl.name(), "/index.html");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

		HttpResponse actual = HttpProcessor.process(httpRequest);

		assertAll(
			() -> assertThat(actual.getHttpStatusLine().getHttpVersion()).isEqualTo("HTTP/1.1"),
			() -> assertThat(actual.getHttpStatusLine().getStatusCode()).isEqualTo(StatusCode.OK),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_TYPE),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_LENGTH),
			() -> assertThat(actual.getHttpRequestBody().getBody()).isEqualTo(
				FileIoUtils.loadFileFromClasspath("./templates/index.html"))
		);
	}

	@DisplayName("GET - /user/form.html 요청")
	@Test
	void processFormHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "GET");
		headers.put(HttpRequestHeaderName.RequestUrl.name(), "/user/form.html");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

		HttpResponse actual = HttpProcessor.process(httpRequest);

		assertAll(
			() -> assertThat(actual.getHttpStatusLine().getHttpVersion()).isEqualTo("HTTP/1.1"),
			() -> assertThat(actual.getHttpStatusLine().getStatusCode()).isEqualTo(StatusCode.OK),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_TYPE),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_LENGTH),
			() -> assertThat(actual.getHttpRequestBody().getBody()).isEqualTo(
				FileIoUtils.loadFileFromClasspath("./templates/user/form.html"))
		);
	}
}