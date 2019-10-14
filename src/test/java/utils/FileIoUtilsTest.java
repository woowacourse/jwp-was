package utils;

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
    void exists() throws Exception {
        boolean actual = FileIoUtils.exists("./templates/index.html");
        assertThat(actual).isTrue();
    }

    @Test
    void notExists() throws Exception {
        boolean actual = FileIoUtils.exists("./templates/asdfasdf.html");
        assertThat(actual).isFalse();
    }
}
