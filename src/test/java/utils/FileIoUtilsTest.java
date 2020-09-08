package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, /index.html", "GET /index.html HTTP/1.1, /index.html", "GET /user/login.html HTTP/1.1, /user/login.html"})
    void extractResourcePath(String input, String expected) {
        String resourcePath = FileIoUtils.extractResourcePath(input);
        assertThat(resourcePath).isEqualTo(expected);
    }
}
