package utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Kind;

public class FileIoUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("HTML 파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_HtmlFileExists_Success() throws Exception {
        byte[] body = FileIoUtils
            .loadFileFromClasspath("/index.html", Kind.WEBAPP_FILE.getDirectory());
        logger.debug("file : {}", new String(body));
    }

    @DisplayName("HTML 파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_HtmlFileNotExists_ThrownException() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("webapp/index.html",
            Kind.WEBAPP_FILE.getDirectory()))
            .isInstanceOf(FileNotExitsException.class);
    }

    @DisplayName("CSS 파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_CssFileExists_Success() throws Exception {
        byte[] body = FileIoUtils
            .loadFileFromClasspath("/css/bootstrap.min.css", Kind.STATIC_FILE.getDirectory());
        logger.debug("file : {}", new String(body));
    }

    @DisplayName("CSS 파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_CssFileNotExists_ThrownException() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("/static/css/bootstrap.min.css",
            Kind.STATIC_FILE.getDirectory()))
            .isInstanceOf(FileNotExitsException.class);
    }
}
