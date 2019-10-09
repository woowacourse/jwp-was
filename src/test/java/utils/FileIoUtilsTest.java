package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.resolver.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("templates/index.html");
        assertThat(body).isNotNull();
        log.debug("file : {}", new String(body));
    }

    @Test
    void loadFileFromClasspath_exception() {
        assertThrows(NotFoundException.class, () -> FileIoUtils.loadFileFromClasspath("./templates/exception.html"));
    }
}
