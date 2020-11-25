package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FileIoUtilsTest {
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
        byte[] body = FileIoUtils.loadFileFromClasspath("/templates/index.html");
    }
}
