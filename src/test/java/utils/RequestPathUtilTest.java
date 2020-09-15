package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestPathUtilTest {

	@Test
	public void getTemplatePath() {
		String request = "GET /index.html HTTP/1.1";

		assertThat(RequestPathUtil.extract(request)).isEqualTo("./templates/index.html");
	}

	@Test
	public void getStaticPath() {
		String request = "GET /index.css HTTP/1.1";

		assertThat(RequestPathUtil.extract(request)).isEqualTo("./static/index.css");
	}

}