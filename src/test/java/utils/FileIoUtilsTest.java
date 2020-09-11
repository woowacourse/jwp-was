package utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_Exists_Success() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_NotExists_ThrownException() throws Exception {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("webapp/index.html"))
            .isInstanceOf(FileNotExitsException.class);
    }
}
