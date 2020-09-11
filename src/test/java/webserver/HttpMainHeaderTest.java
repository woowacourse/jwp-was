package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;

class HttpMainHeaderTest {

	@Test
	@DisplayName("생성자 테스트")
	void init() {
		assertThat(new HttpMainHeader(HttpMethod.GET, "/")).isInstanceOf(HttpMainHeader.class);
	}

	@Test
	@DisplayName("url 처리 테스트 - /index.html")
	void processByIndexUrl() throws IOException, URISyntaxException {
		HttpMainHeader httpMainHeader = new HttpMainHeader(HttpMethod.GET, "/index.html");

		byte[] actual = httpMainHeader.processByUrl();
		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DisplayName("url 처리 테스트 - /user/form.html")
	void processByFromUrl() throws IOException, URISyntaxException {
		HttpMainHeader httpMainHeader = new HttpMainHeader(HttpMethod.GET, "/user/form.html");

		byte[] actual = httpMainHeader.processByUrl();
		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DisplayName("회원 가입 처리 테스트 - /user/create?userId=값&password=값&name=값&email=값")
	void processByCreateUserUrl() throws IOException, URISyntaxException {
		String requestUrl = "/user/create?userId=user&password=1234&name=name&email=email%gmail.com";
		HttpMainHeader httpMainHeader = new HttpMainHeader(HttpMethod.GET, requestUrl);

		byte[] actual = httpMainHeader.processByUrl();
		byte[] expected = "create user success".getBytes();

		assertThat(actual).isEqualTo(expected);
	}
}