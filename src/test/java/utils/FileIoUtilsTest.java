package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        String body = FileIoUtils.loadFileFromClasspath("/index.html").get();
        log.debug("file : {}", body);
    }

    @Test
    @DisplayName("존재하지 않는 HTML 파일을 load 할 경우 빈값을 가져온다.")
    void loadFileFromClasspath2() {
        assertThrows(IllegalArgumentException.class, () ->
                FileIoUtils.loadFileFromClasspath("/test.html")
                        .orElseThrow(IllegalArgumentException::new)
        );
    }

    @Test
    @DisplayName("존재하지 하는 CSS 파일을 Load 한다.")
    void loadFileFromClasspath3() {
        assertTrue(
                FileIoUtils.loadFileFromClasspath("./static/css/styles.css").isPresent()
        );
    }
}
