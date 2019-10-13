package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

	@Test
	void 키값이_있을_때_테스트() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");
		assertThat(httpCookie.hasValueByKey("logined")).isEqualTo(true);
	}

	@Test
	void 키값이_없을_때_테스트() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");
		assertThat(httpCookie.hasValueByKey("login")).isEqualTo(false);
	}

	@Test
	void getPath() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");
		assertThat(httpCookie.getPath()).isEqualTo("/");
	}

	@Test
	void setPath() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");
		httpCookie.setPath("/user/list");
		assertThat(httpCookie.getPath()).isEqualTo("/user/list");
	}

	@Test
	void testToString() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");
		assertThat(httpCookie.toString()).isEqualTo("logined=true");
	}
}