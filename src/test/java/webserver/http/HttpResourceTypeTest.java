package webserver.http;

import exception.FileNotReadableException;
import exception.HttpResourceTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpResourceTypeTest {

    @DisplayName("HttpResourceType에 있는 파일 확장자 문자열로 HttpResourceType 생성 확인")
    @ParameterizedTest
    @ValueSource(strings = {"", "html", "css"})
    void fromTest(String fileExtension) {
        assertThat(HttpResourceType.from(fileExtension)).isInstanceOf(HttpResourceType.class);
    }

    @DisplayName("HttpResourceType에 없는 파일 확장자 문자열로 HttpResourceType 생성 시 HttpResourceTypeNotFoundException 발생")
    @ParameterizedTest
    @ValueSource(strings = {".zip", ".js", ".png", ".wav"})
    void fromExceptionTest(String unknownFileExtension) {
        assertThatThrownBy(() -> HttpResourceType.from(unknownFileExtension))
                .isInstanceOf(HttpResourceTypeNotFoundException.class)
                .hasMessage("처리할 수 없는 파일 확장자입니다! -> " + unknownFileExtension);
    }

    @DisplayName("URI에 따라 resource를 읽기")
    @ParameterizedTest
    @CsvSource(value = {"html, /index.html, ./templates/index.html",
            "css, /css/styles.css, ./static/css/styles.css"})
    void readFileTest(String fileExtension, String uri, String filePath) throws Exception {
        HttpResourceType resourceType = HttpResourceType.from(fileExtension);
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);

        assertThat(resourceType.readFile(uri)).isEqualTo(body);
    }

    @DisplayName("존재하지 않는 resource URI이면 FileNotReadableException 발생")
    @Test
    void readFileExceptionTest() {
        HttpResourceType resourceType = HttpResourceType.from("html");

        assertThatThrownBy(() -> resourceType.readFile("/index2.html"))
                .isInstanceOf(FileNotReadableException.class)
                .hasMessageContaining("읽을 수 없는 파일입니다! -> ");
    }
}