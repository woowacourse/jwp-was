package common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FilePrefixPathMapper;
import utils.StaticFileType;

class FilePrefixPathMapperTest {

    @DisplayName("정적파일의 FullPath를 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, HTML, templates/index.html", "/style.css, CSS, static/style.css"})
    void addPrefix(String path, StaticFileType staticFileType, String actual) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix(path, staticFileType);

        assertThat(filePath).isEqualTo(actual);
    }
}
