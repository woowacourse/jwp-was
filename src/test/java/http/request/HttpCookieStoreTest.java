package http.request;

import http.HttpCookie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieStoreTest {

	@Test
	void addCookie() {
		List cookies = Collections.emptyList();
		HttpCookieStore httpCookieStore = new HttpCookieStore(cookies);
		HttpCookie newCookie = new HttpCookie("domain", "/user/list");
		httpCookieStore.addCookie(newCookie);

		assertThat(httpCookieStore.getCookie("domain")).isEqualTo(newCookie);
	}

	@Test
	void cookieToString() {
		List cookies = Arrays.asList(new HttpCookie("logined", "true"), new HttpCookie("cart", "empty"));
		HttpCookieStore httpCookieStore = new HttpCookieStore(cookies);

		assertThat(httpCookieStore.toString()).isEqualTo("Cookie: logined=true;cart=empty");
	}
}