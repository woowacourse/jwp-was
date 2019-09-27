package webserver.viewProcessor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewProcessorTest {

    @Test
    void 리다이렉트_뷰_처리_성공() {
        RedirectViewProcessor redirectViewProcessor = new RedirectViewProcessor();
        assertThat(redirectViewProcessor.isSupported("/redirect:")).isTrue();
    }

    @Test
    void 리다이렉트_아닌_뷰_처리_성공() {
        RedirectViewProcessor redirectViewProcessor = new RedirectViewProcessor();
        assertThat(redirectViewProcessor.isSupported("/error:")).isFalse();
    }
}