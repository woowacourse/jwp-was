package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    @DisplayName("파일이 있는 경우")
    void existFile() {
        assertThat(FileIoUtils.existFileInClasspath("./templates/index.html")).isTrue();
    }

    @Test
    @DisplayName("파일이 있는 경우")
    void notExistFile() {
        assertThat(FileIoUtils.existFileInClasspath("./templates/not_exists.html")).isFalse();
    }
}
