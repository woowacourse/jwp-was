package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("path에 해당하는 파일을 읽는다.")
    @ParameterizedTest
    @ValueSource(strings = {"./templates/index.html", "./static/css/bootstrap.min.css", "./static/css/styles.css"})
    void loadFileFromClasspath(String filePath) throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        log.debug("file : {}", new String(body));
    }
}
