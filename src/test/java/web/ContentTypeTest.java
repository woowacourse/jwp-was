package web;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ContentTypeTest {

	private static Stream<Arguments> provideContentType() {
		return Stream.of(
			Arguments.of("index.html", ContentType.HTML),
			Arguments.of("styles.css", ContentType.CSS),
			Arguments.of("bootstrap.min.js", ContentType.JS),
			Arguments.of("favicon.ico", ContentType.ICO),
			Arguments.of("a.woff", ContentType.WOFF),
			Arguments.of("image.png", ContentType.PNG),
			Arguments.of("image.jpeg", ContentType.JPEG),
			Arguments.of("image.svg", ContentType.SVG)
		);
	}

	@ParameterizedTest
	@MethodSource("provideContentType")
	void of(String path, ContentType contentType) {
		assertThat(ContentType.of(path)).isEqualTo(contentType);
	}
}