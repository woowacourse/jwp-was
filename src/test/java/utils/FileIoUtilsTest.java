package utils;

import exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
        assertThat(new String(body).length() > 0).isTrue();
    }
    @Test
    void loadMIMEFromClasspath_html_true() {
        String type = FileIoUtils.loadMIMEFromClasspath("./templates/index.html");
        assertThat(type).isEqualTo("text/html");
    }

    @Test
    void loadMIMEFromClasspath_css_true() {
        String type = FileIoUtils.loadMIMEFromClasspath("./static/css/style.css");
        assertThat(type).isEqualTo("text/css");
    }
}
