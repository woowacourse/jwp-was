package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpCookieTest {
	private static final String COOKIE_FIXTURE = "logined=true; anotherCookie=2ndCookie";

	@DisplayName("HttpCookie를 생성한다.")
	@Test
	void createHttpCookieTest() {
		HttpCookie cookie = new HttpCookie(COOKIE_FIXTURE);
		assertThat(cookie.getCookie("logined")).isEqualTo("true");
		assertThat(cookie.getCookie("anotherCookie")).isEqualTo("2ndCookie");
	}
}
