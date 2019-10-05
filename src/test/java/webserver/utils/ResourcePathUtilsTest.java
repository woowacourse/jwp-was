package webserver.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcePathUtilsTest {
	@DisplayName("리소스가 html 일 때 경로 확인")
	@Test
	void getResourcePathWhenHtml() {
		String path = new ResourcePathUtils().getResourcePath("/index.html");
		assertThat(path).isEqualTo("./templates/index.html");
	}

	@DisplayName("리소스가 html 아닐 때 경로 확인")
	@Test
	void getResourcePathWhenEtc() {
		String path = new ResourcePathUtils().getResourcePath("/css/styles.css");
		assertThat(path).isEqualTo("./static/css/styles.css");
	}
}