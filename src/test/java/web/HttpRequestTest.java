package web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.request.HttpMethod;
import web.request.HttpRequest;
import web.request.Parameter;
import web.request.RequestHeader;
import web.request.RequestLine;

class HttpRequestTest {

	@DisplayName("일반적인 POST 요청에 대한 HttpRequest 생성")
	@Test
	void ofIfPostRequest() throws IOException {
		InputStream in = new FileInputStream("./src/test/resources/example.txt");

		Map<String, String> headers = new HashMap<>();
		headers.put("Host", "localhost:8080");
		headers.put("Connection", "keep-alive");
		headers.put("Content-Length", "59");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept", "*/*");

		Map<String, String> parameters = new HashMap<>();
		parameters.put("userId", "javajigi");
		parameters.put("password", "password");
		parameters.put("name", "박재성");
		parameters.put("email", "javajigi@slipp.net");

		RequestLine requestLine = new RequestLine(HttpMethod.POST, "/user/create");

		RequestHeader requestHeader = new RequestHeader(headers);

		Parameter parameter = new Parameter(parameters);

		HttpRequest expect = new HttpRequest(requestLine, requestHeader, parameter);

		HttpRequest actual = HttpRequest.of(in);

		assertAll(
			() -> assertThat(actual.getMethod()).isEqualTo(expect.getMethod()),
			() -> assertThat(actual.getPath()).isEqualTo(expect.getPath()),
			() -> assertThat(actual.getHeader("Content-Length")).isEqualTo(expect.getHeader("Content-Length")),
			() -> assertThat(actual.getParameter("userId")).isEqualTo(expect.getParameter("userId"))
		);
	}

	@DisplayName("일반적인 GET 요청에 대한 HttpRequest 생성")
	@Test
	void ofIfGetRequest() throws IOException {
		InputStream in = new FileInputStream("./src/test/resources/example2.txt");

		Map<String, String> headers = new HashMap<>();
		headers.put("Host", "localhost:8080");
		headers.put("Connection", "keep-alive");
		headers.put("Accept", "*/*");

		RequestLine requestLine = new RequestLine(HttpMethod.GET, "/index.html");

		RequestHeader requestHeader = new RequestHeader(headers);

		Parameter parameter = new Parameter(new HashMap<>());

		HttpRequest expect = new HttpRequest(requestLine, requestHeader, parameter);

		HttpRequest actual = HttpRequest.of(in);

		assertAll(
			() -> assertThat(actual.getMethod()).isEqualTo(expect.getMethod()),
			() -> assertThat(actual.getPath()).isEqualTo(expect.getPath())
		);
	}

	@DisplayName("RequestParams를 가진 POST 요청에 대한 HttpRequest 생성")
	@Test
	public void ofIfPostRequestHasRequestParameters() throws Exception {
		InputStream in = new FileInputStream("./src/test/resources/example3.txt");
		HttpRequest request = HttpRequest.of(in);

		assertEquals("POST", request.getMethod().name());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("1", request.getParameter("id"));
		assertEquals("javajigi", request.getParameter("userId"));
	}
}