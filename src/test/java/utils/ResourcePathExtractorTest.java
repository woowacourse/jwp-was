package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcePathExtractorTest {

    @DisplayName("정적 파일의 path를 추출한다.")
    @Test
    void extractName() {
        List<ResourcePath> paths = ResourcePathExtractor.extract("./static");

        List<String> filePath = paths.stream()
                .map(ResourcePath::getFilePath)
                .collect(Collectors.toList());
        assertThat(filePath).contains("./static/index.html", "./static/css/style.css", "./static/js/scripts.js", "./static/css/bootstrap/boot.css");
    }
}