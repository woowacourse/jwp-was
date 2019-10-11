package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("정적 파일 불러오기")
    @Test
    void loadFileFromClasspath_css_true() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./static/css/styles.css");
        assertThat(new String(body).length() > 0).isTrue();
    }

    @DisplayName("템플릿 파일 불러오기")
    @Test
    void loadFileFromClasspath_html_true() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(new String(body).length() > 0).isTrue();
    }

    @DisplayName("html파일 type가져오기")
    @Test
    void loadMIMEFromClasspath_html_true() {
        String type = FileIoUtils.loadMIMEFromClasspath("./templates/index.html");
        assertThat(type).isEqualTo("text/html");
    }

    @DisplayName("css파일 type가져오기")
    @Test
    void loadMIMEFromClasspath_css_true() {
        String type = FileIoUtils.loadMIMEFromClasspath("./static/css/style.css");
        assertThat(type).isEqualTo("text/css");
    }
}
