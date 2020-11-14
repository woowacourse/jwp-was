package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void checkInvalidFilePath() throws Exception {
        String url = "invalidPath";
        byte[] result = FileIoUtils.loadFileFromClasspath(url);
        assertThat(result).isEmpty();
    }
}
