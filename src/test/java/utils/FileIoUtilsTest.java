package utils;

import exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("존재하지 않는 파일일 때 예외 처리")
    @Test
    void invalidPathExceptionTest() {
        final String NOT_EXIST_PATH = "./does_not_exist";
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath(NOT_EXIST_PATH))
                .isInstanceOf(CustomException.class);
    }
}
