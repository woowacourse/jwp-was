package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void 파일이_존재하지_않는_경우_FileNotFoundException_발생() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("not/exsist"))
                .isInstanceOf(FileNotFoundException.class);
    }
}
