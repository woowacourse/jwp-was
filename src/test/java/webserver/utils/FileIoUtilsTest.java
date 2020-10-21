package webserver.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("주어진 경로에 위치한 파일을 읽는다.")
    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
        assertAll(
            () -> assertThat(new String(body)).contains(("<html lang=\"kr\">")),
            () -> assertThat(new String(body)).contains(("<title>SLiPP Java Web Programming</title>"))
        );
    }
}
