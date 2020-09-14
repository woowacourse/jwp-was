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
import webserver.exception.NotExistUrlException;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeaderName;
import webserver.http.request.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponse;
import webserver.http.response.StatusCode;
import webserver.process.get.GetUrlProcessor;

class GetUrlProcessorTest {

	@DisplayName("올바르지 않은 GET Method Url 요청")
	@Test
	void apply() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "GET");
		headers.put(HttpRequestHeaderName.RequestUrl.name(), "/올바르지않은url");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

		assertThatThrownBy(() -> new GetUrlProcessor().apply(httpRequest))
			.isInstanceOf(NotExistUrlException.class);
	}

	@DisplayName("/index.html 요청 처리")
	@Test
	void applyByIndexHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "GET");
		headers.put(HttpRequestHeaderName.RequestUrl.name(), "/index.html");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

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
	void applyByUserFormHtml() throws IOException, URISyntaxException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "GET");
		headers.put(HttpRequestHeaderName.RequestUrl.name(), "/user/form.html");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody("");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

		HttpResponse actual = new GetUrlProcessor().apply(httpRequest);

		assertAll(
			() -> assertThat(actual.getHttpStatusLine().getHttpVersion()).isEqualTo("HTTP/1.1"),
			() -> assertThat(actual.getHttpStatusLine().getStatusCode()).isEqualTo(StatusCode.OK),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_TYPE),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(CONTENT_LENGTH),
			() -> assertThat(actual.getHttpRequestBody().getBody()).isEqualTo(
				FileIoUtils.loadFileFromClasspath("./templates/user/form.html"))
		);

		// HttpStatusLine httpStatusLine = new HttpStatusLine(StatusCode.OK);
		// String body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
		// Map<HttpResponseHeaderName, String> responseHeaders = new HashMap<>();
		// responseHeaders.put(CONTENT_TYPE, "text/html;charset=utf-8");
		// responseHeaders.put(CONTENT_LENGTH, String.valueOf(body.length()));
		// HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders(responseHeaders);
		//
		// HttpResponse expected = new HttpResponse(httpStatusLine, httpResponseHeaders, new HttpRequestBody(body));
		//
		// assertThat(actual).isEqualTo(expected);
	}
}