package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("파일을 읽을 수 있는 지 확인한다.")
    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("파일이 없으면 예외처리 한다.")
    @Test
    void loadFileFromClasspathWithException() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("이건 없지롱"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("없습니다.");
    }
}
