package webserver.http.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestStartLineTest {

	@DisplayName("url 정보 확인 - 참")
	@Test
	void isSameUrl() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/url", "HTTP/1.1");

		assertTrue(httpRequestStartLine.isSameUrl("/url"));
	}

	@DisplayName("url 정보 확인 - 거짓")
	@Test
	void isNotSameUrl() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/url", "HTTP/1.1");

		assertFalse(httpRequestStartLine.isSameUrl("/anotherurl"));
	}

	@DisplayName("url에 포함되는지 확인 - 참")
	@Test
	void containsInUrl() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/contains/url", "HTTP/1.1");

		assertTrue(httpRequestStartLine.containsInUrl("/contains"));
	}

	@DisplayName("url에 포함되는지 확인 - 거짓")
	@Test
	void containsNotInUrl() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "/contains/url", "HTTP/1.1");

		assertFalse(httpRequestStartLine.containsInUrl("/another/url"));
	}

	@DisplayName("POST가 아닌 method type인 경우 바디를 포함하지 않는다.")
	@Test
	void hasNotBody() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "", "HTTP/1.1");

		assertTrue(httpRequestStartLine.hasNotBody());
	}

	@DisplayName("POST method type인 경우 바디를 포함")
	@Test
	void hasBody() {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("POST", "", "HTTP/1.1");

		assertFalse(httpRequestStartLine.hasNotBody());
	}

}