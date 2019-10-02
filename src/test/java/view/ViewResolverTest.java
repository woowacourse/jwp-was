package view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewResolverTest {
    @Test
    void resolveRedirectView() {
        String redirectViewName = "redirect: /";
        assertTrue(ViewResolver.getInstance().resolve(redirectViewName) instanceof RedirectView);
    }

    @Test
    void resolveTemplateView() {
        String redirectViewName = "/";
        assertTrue(ViewResolver.getInstance().resolve(redirectViewName) instanceof TemplateView);
    }

    @Test
    void resolveDefaultView() {
        String redirectViewName = "unkownPath";
        assertTrue(ViewResolver.getInstance().resolve(redirectViewName) instanceof TemplateView);
    }
}