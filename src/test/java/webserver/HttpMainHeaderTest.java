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
	@DisplayName("url 처리 테스트 - index.html")
	void processByUrl() throws IOException, URISyntaxException {
		HttpMainHeader httpMainHeader = new HttpMainHeader(HttpMethod.GET, "/index.html");

		byte[] actual = httpMainHeader.processByUrl();
		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");

		assertThat(actual).isEqualTo(expected);
	}
}