package webserver.utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.FileNameExtension;

public class FileIoUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("HTML 파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_HtmlFileExists_Success() throws Exception {
        String filePath = "/index.html";
        byte[] body = FileIoUtils
            .loadFileFromClasspath(filePath, FileNameExtension.from(filePath).getDirectory());
        logger.debug("file : {}", new String(body));
    }

    @DisplayName("HTML 파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_HtmlFileNotExists_ThrownException() {
        String filePath = "webapp/index.html";
        assertThatThrownBy(() -> FileIoUtils
            .loadFileFromClasspath(filePath, FileNameExtension.from(filePath).getDirectory()))
            .isInstanceOf(FileNotExitsException.class);
    }

    @DisplayName("CSS 파일 불러오는지 확인, 성공 - 파일 존재")
    @Test
    void loadFileFromClasspath_CssFileExists_Success() throws Exception {
        String filePath = "/css/bootstrap.min.css";
        byte[] body = FileIoUtils
            .loadFileFromClasspath(filePath, FileNameExtension.from(filePath).getDirectory());
        logger.debug("file : {}", new String(body));
    }

    @DisplayName("CSS 파일 불러오는지 확인, 예외 발생 - 파일 존재하지 않음")
    @Test
    void loadFileFromClasspath_CssFileNotExists_ThrownException() {
        String filePath = "/static/css/bootstrap.min.css";

        assertThatThrownBy(() -> FileIoUtils
            .loadFileFromClasspath(filePath, FileNameExtension.from(filePath).getDirectory()))
            .isInstanceOf(FileNotExitsException.class);
    }
}
