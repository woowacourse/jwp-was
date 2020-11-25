package server.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.TestWebServerApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PathExtractorTest {

    @DisplayName("정적 파일의 path를 추출한다.")
    @Test
    void extractName() {
        List<ResourcePath> paths = PathExtractor.extractResourcePath("./static");

        List<String> filePath = paths.stream()
                .map(ResourcePath::getFilePath)
                .collect(Collectors.toList());
        assertThat(filePath).contains("./static/index.html", "./static/css/style.css", "./static/js/scripts.js", "./static/css/bootstrap/boot.css");
    }

    @Test
    void extractSourcePath() {
        List<String> sourcePaths = PathExtractor.extractSourcePath(TestWebServerApplication.class);

        assertThat(sourcePaths).contains("server.controller.TestController");
    }

}