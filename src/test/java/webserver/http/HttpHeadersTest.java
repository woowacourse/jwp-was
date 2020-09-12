package webserver.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {

	@DisplayName("url 정보 확인 - 참")
	@Test
	void isSameUrl() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.RequestUrl.name(), "/url");
		HttpHeaders httpHeaders = new HttpHeaders(headers);

		assertTrue(httpHeaders.isSameUrl("/url"));
	}

	@DisplayName("url 정보 확인 - 거짓")
	@Test
	void isNotSameUrl() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.RequestUrl.name(), "/url");
		HttpHeaders httpHeaders = new HttpHeaders(headers);

		assertFalse(httpHeaders.isSameUrl("/anotherurl"));
	}

	@DisplayName("url에 포함되는지 확인 - 참")
	@Test
	void containsInUrl() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.RequestUrl.name(), "/contains/url");
		HttpHeaders httpHeaders = new HttpHeaders(headers);

		assertTrue(httpHeaders.containsInUrl("/contains"));
	}

	@DisplayName("url에 포함되는지 확인 - 거짓")
	@Test
	void containsNotInUrl() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.RequestUrl.name(), "/contains/url");
		HttpHeaders httpHeaders = new HttpHeaders(headers);

		assertFalse(httpHeaders.containsInUrl("/another/url"));
	}

	@DisplayName("POST가 아닌 method type인 경우 바디를 포함하지 않는다.")
	@Test
	void hasNotBody() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		HttpHeaders httpHeaders = new HttpHeaders(headers);

		assertTrue(httpHeaders.hasNotBody());
	}

	@DisplayName("POST method type인 경우 바디를 포함")
	@Test
	void hasBody() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "POST");
		HttpHeaders httpHeaders = new HttpHeaders(headers);

		assertFalse(httpHeaders.hasNotBody());
	}
}