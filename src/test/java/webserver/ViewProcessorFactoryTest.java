package webserver;

import org.junit.jupiter.api.Test;
import webserver.viewProcessor.ErrorViewProcessor;
import webserver.viewProcessor.HtmlViewProcessor;
import webserver.viewProcessor.RedirectViewProcessor;
import webserver.viewProcessor.ResourceViewProcessor;

import static org.assertj.core.api.Assertions.assertThat;

class ViewProcessorFactoryTest {

    @Test
    void ErrorViewProcessor_생성_확인() {
        ViewProcessor viewProcessor = ViewProcessorFactory.getInstance().getViewProcessor("/error:");
        assertThat(viewProcessor).isInstanceOf(ErrorViewProcessor.class);
    }

    @Test
    void RedirectViewProcessor_생성_확인() {
        ViewProcessor viewProcessor = ViewProcessorFactory.getInstance().getViewProcessor("/redirect:");
        assertThat(viewProcessor).isInstanceOf(RedirectViewProcessor.class);
    }

    @Test
    void HtmlProcessor_생성_확인() {
        ViewProcessor viewProcessor = ViewProcessorFactory.getInstance().getViewProcessor("index.html");
        assertThat(viewProcessor).isInstanceOf(HtmlViewProcessor.class);
    }

    @Test
    void ResourceViewProcessor_생성_확인() {
        ViewProcessor viewProcessor = ViewProcessorFactory.getInstance().getViewProcessor("abc.css");
        assertThat(viewProcessor).isInstanceOf(ResourceViewProcessor.class);
    }

}