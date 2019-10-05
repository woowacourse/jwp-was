package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void 정상적인_확장자_가져오기() {
        String file = "index.html";
        assertThat(FileIoUtils.getExtension(file).get()).isEqualTo("html");
    }

    @Test
    void 확장자가_없는_파일_확장자_가져오기() {
        String file = "/user/create";
        assertThat(FileIoUtils.getExtension(file)).isEmpty();
    }
}
