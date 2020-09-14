package webserver.process.post;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static webserver.http.response.HttpResponseHeaderName.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeaderName;
import webserver.http.request.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponse;
import webserver.http.response.StatusCode;
import webserver.process.HttpProcessor;

class PostUrlProcessorTest {

	@DisplayName("/user/create 요청 처리")
	@Test
	void apply() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "POST");
		headers.put(HttpRequestHeaderName.RequestUrl.name(), "/user/create");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody(
			"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
		HttpRequest httpRequest = new HttpRequest(httpRequestHttpHeaders, httpRequestBody);

		HttpResponse actual = HttpProcessor.process(httpRequest);

		assertAll(
			() -> assertThat(actual.getHttpStatusLine().getHttpVersion()).isEqualTo("HTTP/1.1"),
			() -> assertThat(actual.getHttpStatusLine().getStatusCode()).isEqualTo(StatusCode.FOUND),
			() -> assertThat(actual.getHttpResponseHeaders().getHttpHeaders()).containsKey(LOCATION),
			() -> assertThat(actual.getHttpRequestBody().getBody()).isEqualTo("create user : userId=javajigi")
		);
	}
}