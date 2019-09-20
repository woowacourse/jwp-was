package webserver.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.NotMatchUrlException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PathHandlerTest {

    @Test
    @DisplayName("templates 폴더의 파일 절대 경로")
    public void templatesFilePath() {
        String url = "/index.html";
        String expected = "./templates/index.html";
        assertThat(PathHandler.path(url)).isEqualTo(expected);
    }

    @Test
    @DisplayName("static 폴더의 파일 절대 경로")
    public void staticFilePath() {
        String url = "/min.css";
        String expected = "./static/min.css";
        assertThat(PathHandler.path(url)).isEqualTo(expected);
    }

    @Test
    @DisplayName("확장자에 해당하지 않는 파일일때")
    public void notMatchUrlException() {
        String url = "/min.cs";
        assertThrows(NotMatchUrlException.class, () -> PathHandler.path(url));
    }
}