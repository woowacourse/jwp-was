package webserver.response;

public interface TemplateEngine {
    String render(ModelAndView modelAndView);
}
