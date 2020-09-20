package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ResourceTypeMatcherTest {
	@ParameterizedTest
	@CsvSource({"/a.html,HTML", "/b.js,JS", "/c.css,CSS", "/d.woff,WOFF"})
	void findContentTypeTest(String file, ResourceTypeMatcher type) {
		assertThat(ResourceTypeMatcher.findContentType(file)).isEqualTo(type);
	}

	@ParameterizedTest
	@CsvSource({"/a.html,HTML,./templates/a.html", "/b.js,JS,./static/b.js", "/c.css,CSS,./static/c.css",
		"/d.woff,WOFF,./static/d.woff"})
	void parseFilePathTest(String file, ResourceTypeMatcher type, String path) {
		assertThat(type.parseFilePath(file)).isEqualTo(path);
	}

	@ParameterizedTest
	@CsvSource({"/a.html,true", "/b.js,true", "/c.css,true", "d.woff,true", "e.hwp,false", "e.docs,false"})
	void isContainTypeTest(String file, boolean expected) {
		assertThat(ResourceTypeMatcher.isContainType(file)).isEqualTo(expected);
	}
}
