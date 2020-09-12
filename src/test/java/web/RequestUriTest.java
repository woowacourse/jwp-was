package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriTest {

    @DisplayName("요청 경로가 정적 파일인지 확인할 수 있다.")
    @Test
    void name() {
        RequestUri htmlFile = new RequestUri("/index.html");
        RequestUri cssFile = new RequestUri("./css/styles.css");
        RequestUri jsFile = new RequestUri("./js/scripts.js");
        RequestUri notStaticRequest = new RequestUri("/use");

        assertThat(htmlFile.isStaticFile()).isTrue();
        assertThat(cssFile.isStaticFile()).isTrue();
        assertThat(jsFile.isStaticFile()).isTrue();
        assertThat(notStaticRequest.isStaticFile()).isFalse();
    }

}