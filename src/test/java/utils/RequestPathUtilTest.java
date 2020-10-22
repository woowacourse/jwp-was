package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class RequestPathUtilTest {

	@Test
	public void getTemplatePath() {
		String request = "/index.html";

		assertThat(RequestPathUtil.extractFilePath(request)).isEqualTo("./templates/index.html");
	}

	@Test
	public void getStaticPath() {
		String request = "/index.css";

		assertThat(RequestPathUtil.extractFilePath(request)).isEqualTo("./static/index.css");
	}
}