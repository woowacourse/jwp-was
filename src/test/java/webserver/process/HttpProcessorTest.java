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
import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponse;
import webserver.http.response.StatusCode;

class HttpProcessorTest {

	@DisplayName("잘못된 Method가 들어왔을 경우 - IllegalArgumentException 발생")
	@Test
	void processException() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("잘못된 METHOD", "/url", "HTTP/1.1");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(new HashMap<>());
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);

		assertThatThrownBy(() -> HttpProcessor.process(httpRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("GET - index.html 요청")
	@Test
	void processIndexHtml() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/index.html", "HTTP/1.1");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(new HashMap<>());
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);

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
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/user/form.html", "HTTP/1.1");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(new HashMap<>());
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);

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