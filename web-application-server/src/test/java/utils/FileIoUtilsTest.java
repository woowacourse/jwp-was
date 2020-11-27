package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("/test.html");
        log.debug("file : {}", new String(body));
    }


    @ParameterizedTest
    @ValueSource(strings = {"/css/test.css", "/js/test.js", "/images/test.png", "/test.ico"})
    void loadFileFromStatic(String path) throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        log.debug("file : {}", new String(body));
    }
}
