package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        String body = FileIoUtils.loadFileFromClasspath("./templates/index.html").get();
        log.debug("file : {}", body);
    }
}
