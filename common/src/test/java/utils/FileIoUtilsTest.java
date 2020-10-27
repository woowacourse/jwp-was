package utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Disabled
    @DisplayName("파일 찾기 테스트")
    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("존재하지 않은 파일의 경로 입력한 경우")
    @Test
    void loadFileFromClasspathWithNullPointException() throws IOException, URISyntaxException {
        assertThat(FileIoUtils.loadFileFromClasspath("a").length).isZero();
    }
}
