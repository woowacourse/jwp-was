package jwp.was.webserver.utils;

import static jwp.was.util.Constants.URL_PATH_BOOTSTRAP_MIN_CSS;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileIoUtilsTest.class);
    private static final String WRONG_PREFIX_PATH = "/abc";

    @DisplayName("HTML 파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_HtmlFileExists_Success() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(URL_PATH_INDEX_HTML.getUrlPath());
        LOGGER.debug("file : {}", new String(body));
    }

    @DisplayName("HTML 파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_HtmlFileNotExists_ThrownException() {
        String filePath = WRONG_PREFIX_PATH + URL_PATH_INDEX_HTML.getUrlPath();
        assertThatThrownBy(() -> FileIoUtils
            .loadFileFromClasspath(filePath))
            .isInstanceOf(FileNotExitsException.class);
    }

    @DisplayName("CSS 파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_CssFileExists_Success() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(URL_PATH_BOOTSTRAP_MIN_CSS.getUrlPath()
        );
        LOGGER.debug("file : {}", new String(body));
    }

    @DisplayName("CSS 파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_CssFileNotExists_ThrownException() {
        String filePath = WRONG_PREFIX_PATH + URL_PATH_BOOTSTRAP_MIN_CSS;

        assertThatThrownBy(() -> FileIoUtils
            .loadFileFromClasspath(filePath))
            .isInstanceOf(FileNotExitsException.class);
    }
}
