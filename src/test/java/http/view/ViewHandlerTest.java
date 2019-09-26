package http.view;

import http.model.ContentType;
import http.supoort.ResolverMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewHandlerTest {
    private ViewHandler viewHandler;

    @BeforeEach
    void setUp() {
        viewHandler = new ViewHandler();
        viewHandler.addResolver(new ResolverMapping("\\/.*\\.html"), new ViewTemplatesResolver());
        viewHandler.addResolver(new ResolverMapping("\\/.*\\.(css|js|png)"), new ViewStaticResolver());
    }

    @Test
    void ViewStaticResolver_선택() {
        View view = new View("/css/bootstrap.min.css");
        ModelAndView modelAndView = new ModelAndView(view);
        assertThat(viewHandler.handle(modelAndView).getHeader("Content-Type")).isEqualTo(ContentType.CSS.getType());
    }

    @Test
    void ViewTemplatesResolver_선택() {
        View view = new View("/index.html");
        ModelAndView modelAndView = new ModelAndView(view);
        assertThat(viewHandler.handle(modelAndView).getHeader("Content-Type")).isEqualTo(ContentType.HTML.getType());
    }
}