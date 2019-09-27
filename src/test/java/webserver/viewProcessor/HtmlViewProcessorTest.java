package webserver.viewProcessor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlViewProcessorTest {

    @Test
    void HTML_뷰_처리_성공() {
        HtmlViewProcessor htmlViewProcessor = new HtmlViewProcessor();
        assertThat(htmlViewProcessor.isSupported(".html")).isTrue();
    }

    @Test
    void HTML_아닌_뷰_처리_성공() {
        HtmlViewProcessor htmlViewProcessor = new HtmlViewProcessor();
        assertThat(htmlViewProcessor.isSupported(".css")).isFalse();
    }

}