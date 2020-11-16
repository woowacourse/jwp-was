package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void findStaticFile() throws Exception {
        byte[] body = FileIoUtils.findStaticFile("/index.html");
        assertThat(body).isNotNull();
    }

    @Test
    void findNotExistFile() throws Exception {
        byte[] body = FileIoUtils.findStaticFile("/i.css");
        assertThat(body).isNull();
    }

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }
}
