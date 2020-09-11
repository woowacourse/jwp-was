package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("파일 불러오는지 확인")
    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("index.html");
        log.debug("file : {}", new String(body));
    }
}
