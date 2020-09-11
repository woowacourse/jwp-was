package webserver.process;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;

class GetUrlProcessorTest {

	@DisplayName("올바르지 않은 GET Method Url 요청")
	@Test
	void apply() {
		assertThatThrownBy(() -> new GetUrlProcessor().apply(""))
			.isInstanceOf(NotExistUrlException.class);
	}

	@DisplayName("/index.html 요청 처리")
	@Test
	void applyByIndexHtml() throws IOException, URISyntaxException {
		byte[] actual = new GetUrlProcessor().apply("/index.html");

		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");

		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("/user/form.html 요청 처리")
	@Test
	void applyByUserFormHtml() throws IOException, URISyntaxException {
		byte[] actual = new GetUrlProcessor().apply("/user/form.html");

		byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");

		assertThat(actual).isEqualTo(expected);
	}
}