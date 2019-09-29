package http.request;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest {

	@Test
	void ContentLength_있음() {
		HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Collections.singletonMap("Content-Length", "6902"), new HttpCookieStore(Collections.emptyList()));
		assertThat(httpRequestHeader.getContentLength()).isEqualTo(6902);
	}

	@Test
	void ContentLength_없음() {
		HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Collections.singletonMap("Content-Type", "text/html;charset=utf-8"), new HttpCookieStore(Collections.emptyList()));
		assertThat(httpRequestHeader.getContentLength()).isEqualTo(0);
	}
}