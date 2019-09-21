package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcePathUtilsTest {
	@Test
	void getResourcePathWhenHtml() {
		String path = new ResourcePathUtils().getResourcePath("/index.html");
		assertThat(path).isEqualTo("./templates/index.html");
	}

	@Test
	void getResourcePathWhenEtc() {
		String path = new ResourcePathUtils().getResourcePath("/css/styles.css");
		assertThat(path).isEqualTo("./static/css/styles.css");
	}
}