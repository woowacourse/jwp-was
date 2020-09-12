package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @DisplayName("요청 경로가 정적 파일인지 확인할 수 있다.")
    @Test
    void name() {
        Path htmlFile = new Path("/index.html");
        Path cssFile = new Path("./css/styles.css");
        Path jsFile = new Path("./js/scripts.js");
        Path notStaticRequest = new Path("/use");

        assertThat(htmlFile.isStaticFile()).isTrue();
        assertThat(cssFile.isStaticFile()).isTrue();
        assertThat(jsFile.isStaticFile()).isTrue();
        assertThat(notStaticRequest.isStaticFile()).isFalse();
    }
}