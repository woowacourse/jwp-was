package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void 파일이_있는_경우() throws URISyntaxException {
        assertThat(FileIoUtils.existFileInClasspath("./templates/index.html")).isTrue();
    }

    @Test
    void 파일이_없는_경우() throws URISyntaxException {
        assertThat(FileIoUtils.existFileInClasspath("./templates/not_exists.html")).isFalse();
    }
}
