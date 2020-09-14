package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ContentTypeMatcherTest {
	@ParameterizedTest
	@CsvSource({"/a.html,HTML", "/b.js,JS", "/c.css,CSS"})
	void findContentTypeTest(String file, ContentTypeMatcher type) {
		assertThat(ContentTypeMatcher.findContentType(file)).isEqualTo(type);
	}

	@ParameterizedTest
	@CsvSource({"/a.html,HTML,./templates/a.html", "/b.js,JS,./static/b.js", "/c.css,CSS,./static/c.css"})
	void parseFilePathTest(String file, ContentTypeMatcher type, String path) {
		assertThat(type.parseFilePath(file)).isEqualTo(path);
	}
}
