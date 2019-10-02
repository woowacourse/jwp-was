package webserver.viewProcessor;

import org.junit.jupiter.api.Test;
import webserver.View;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorViewProcessorTest {

    @Test
    void 에러뷰_처리_성공() {
        ErrorViewProcessor errorViewProcessor = new ErrorViewProcessor();
        assertThat(errorViewProcessor.isSupported(new View("/error:"))).isTrue();
    }

    @Test
    void 에러_아닌_뷰_처리_실패() {
        ErrorViewProcessor errorViewProcessor = new ErrorViewProcessor();
        assertThat(errorViewProcessor.isSupported(new View("/index.html"))).isFalse();
    }
}