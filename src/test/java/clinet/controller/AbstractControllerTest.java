package clinet.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import web.controller.Controller;

class AbstractControllerTest {

	@DisplayName("URI가 ContentType을 포함할 경우, ResourceController 반환")
	@ParameterizedTest
	@CsvSource(value = {"/index.html", "/styles.css", "/bootstrap.min.js", "favicon.ico", "a.woff", "image.png"})
	void findControllerIfUriIsNone(String path) throws Exception {
		Controller actual = AbstractController.findController(path);
		assertThat(actual).isInstanceOf(ResourceController.class);
	}

	@DisplayName("URI가 '/user/create'일 경우, CreateUserController 반환")
	@Test
	void findController() throws Exception {
		Controller actual = AbstractController.findController("/user/create");
		assertThat(actual).isInstanceOf(CreateUserController.class);
	}
}