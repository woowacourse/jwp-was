package view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static view.ViewResolver.REDIRECT_SIGNATURE;

class ViewResolverTest {
    private ViewResolver viewResolver = ViewResolver.getInstance();

    @Test
    void resolveTest() {
        assertEquals(viewResolver.resolve("/test").getClass(), TemplateView.class);
        assertEquals(viewResolver.resolve(String.format("%s%s", REDIRECT_SIGNATURE, "/test")).getClass(), RedirectView.class);
    }
}