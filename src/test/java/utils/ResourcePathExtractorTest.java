package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcePathExtractorTest {

    @DisplayName("정적 파일의 path를 추출한다.")
    @Test
    void extractName() throws IOException {
        List<String> paths = ResourcePathExtractor.extract("./static");

        assertThat(paths).contains("/index.html", "/css/style.css", "/js/scripts.js", "/css/bootstrap/boot.css");
    }
}