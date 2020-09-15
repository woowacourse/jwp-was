package webserver.process;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static webserver.http.response.HttpResponseHeaderName.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponse;
import webserver.http.response.StatusCode;
import webserver.process.get.GetUrlProcessor;

class GetUrlProcessorTest {

	@DisplayName("올바르지 않은 GET Method Url 요청")
	@Test
	void apply() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/올바르지않은url", "HTTP/1.1");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(new HashMap<>());
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);

		assertThatThrownBy(() -> new GetUrlProcessor().apply(httpRequest))
			.isInstanceOf(NotExistUrlException.class);
	}

	@DisplayName("/index.html 요청 처리")
	@Test
	void applyByIndexHtml() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/index.html", "HTTP/1.1");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(new HashMap<>());
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);

		HttpResponse actual = new GetUrlProcessor().apply(httpRequest);

		assertAll(
			() -> assertThat(actual.getHttpStatusLine().getHttpVersion()).isEqualTo("HTTP/1.1"),
			() -> assertThat(actual.getHttpStatusLine().getStatusCode()).isEqualTo(StatusCode.OK),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_TYPE),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_LENGTH),
			() -> assertThat(actual.getHttpRequestBody().getBody()).isEqualTo(
				FileIoUtils.loadFileFromClasspath("./templates/index.html"))
		);
	}

	@DisplayName("/user/form.html 요청 처리")
	@Test
	void applyByUserFormHtml() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/user/form.html", "HTTP/1.1");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(new HashMap<>());
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);

		HttpResponse actual = new GetUrlProcessor().apply(httpRequest);

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